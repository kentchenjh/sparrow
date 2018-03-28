package com.sparrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sparrow.aop.Aspect;
import com.sparrow.aop.AspectManager;
import com.sparrow.aop.annotation.Aop;
import com.sparrow.ioc.ClassInfo;
import com.sparrow.ioc.EasyIoc;
import com.sparrow.ioc.Ioc;
import com.sparrow.ioc.Scanner;
import com.sparrow.ioc.annotation.Bean;
import com.sparrow.kit.CollectionKit;
import com.sparrow.kit.IocKit;
import com.sparrow.kit.ReflectKit;
import com.sparrow.mvc.adapter.RouteAdapter;
import com.sparrow.mvc.annotation.Controller;
import com.sparrow.mvc.annotation.Hook;
import com.sparrow.mvc.annotation.Service;
import com.sparrow.mvc.handler.ExceptionHandler;
import com.sparrow.mvc.handler.StaticsHandler;
import com.sparrow.mvc.http.HttpMethod;
import com.sparrow.mvc.template.TemplateEngine;
import com.sparrow.proxy.CglibProxyManger;
import com.sparrow.proxy.JdkProxyManager;
import com.sparrow.route.RouteHandler;
import com.sparrow.route.RouteManager;
import com.sparrow.server.JettyServer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Sparrow {

	public static final String STATICS_HANDLER_BEAN_NAME 		= "staticsHandler";
	
	public static final String TEMPLATE_ENGINE_BEAN_NAME 		= "templateEngine";
	
	public static final String EXCEPTION_HANDLER_BEAN_NAME 		= "exceptionHandler";
	
	public static final String ROUTE_ADAPTERS_BEAN_NAME  		= "routeAdapters";
	
	private Env env = Env.empty();
	
	private RouteManager routeManager = new RouteManager();
	
	private AspectManager aspectManager = new AspectManager();
	
	private Ioc ioc = new EasyIoc();
	
	//fixed name for TemplateEngine
	private TemplateEngine templateEngine;
	
	//fixed name for ExceptionHandler
	private ExceptionHandler exceptionHandler;
	
	//fixed name for StaticsHandler
	private StaticsHandler staticsHandler;
	
	//fixed name for route adapter list
	private List<RouteAdapter> routeAdapters;
	
	private List<String> packages = new ArrayList<>();
	
	private Set<String> statics = new HashSet<>(Const.DEFAULT_STATICS);
	
	private Set<String> configs = new HashSet<>(Const.CONFIG_PATH);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	private JettyServer server;
	
	private Sparrow() {};
	
	public static Sparrow me() {
		return new Sparrow();
	}
	
	public Sparrow env(Env env) {
		
		if(null != env) {
			this.env = env;
		}
		return this;
	}
	
	public Sparrow register(Class<?> cls) {
		return register(cls.getName(), cls);
	}
	
	private Sparrow register(String name, Class<?> cls) {
		ioc.addBean(name, new ClassInfo(cls));
		return this;
	}
	
	private void init() {
		initConfig();
		initIoc();
		initComponent();
	}

	private void initComponent() {
		//将handler注入
		injectComponent(STATICS_HANDLER_BEAN_NAME, Const.SYS_STATICS_HANDLER);
		injectComponent(EXCEPTION_HANDLER_BEAN_NAME, Const.SYS_EXCEPTION_HANDLER);
		injectComponent(TEMPLATE_ENGINE_BEAN_NAME, Const.SYS_TEMPLATE_ENGINE);
		injectComponent(ROUTE_ADAPTERS_BEAN_NAME, Const.SYS_ROUTE_ADAPTER);
	}

	private void injectComponent(String fieldName, String defaultComponent) {
		
		Object component = ioc.getBean(fieldName);
		if(component == null) {
			Optional<String> defaultHandlerName = env.get(defaultComponent);
			if(defaultHandlerName.isPresent()) {
				String handlerName = defaultHandlerName.get();
				if(handlerName.contains("|")) {
					List<Object> components = new ArrayList<>();
					for(String s : handlerName.split("\\|")) {
						components.add(ReflectKit.getInstanceByFullName(s));
					}
					ReflectKit.injectField(this, fieldName, components);
					return;
				} else {
					component = ReflectKit.getInstanceByFullName(handlerName);
				}
			}
		}
		ReflectKit.injectField(this, fieldName, component);
		log.info("inject {} : {}", fieldName, component);
	}

	private void initConfig() {
		//load config file
		env.of(configs);
		//add base packages
		packages.add(env.get(Const.BASE_PACKAGE, ""));
	}

	private void initIoc() {
		
		//scan all beans 
		Set<ClassInfo> classInfos = packages.stream()
				.map(Scanner::scan)
				.flatMap(Set::stream)
				.collect(Collectors.toSet());
		
		if(!CollectionKit.isEmpty(classInfos)) {
			classInfos.stream().map(ClassInfo::getClazz).forEach(this::processClazz);
		}
		
		//aop
		ioc.getClassInfos().stream()
			.filter(ci -> 
				ci.getClazz().isAnnotationPresent(Controller.class) ||
				ci.getClazz().isAnnotationPresent(Service.class))
			.forEach(this::aopProxy);
		
		//injection for loaded beans
		ioc.getClassInfos().forEach(classInfo -> {
			IocKit.refInjection(ioc, classInfo);
			IocKit.valueInjection(env, classInfo);
		});
		
		log.info("ioc container content: {}", ioc.getBeans());
	}
	
	private void aopProxy(ClassInfo ci) {
		
		try {
			Class<?> clazz = ci.getClazz();
			Object instance = ci.getInstance();
			
			if(!CollectionKit.isEmpty(clazz.getInterfaces())) {
				instance = JdkProxyManager.newAopProxy(instance, aspectManager);
			} else {
				instance = CglibProxyManger.newAopProxy(instance, aspectManager);
			}
			ci.setInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processClazz(Class<?> cls) {
		//drop the class without annotations
		if(CollectionKit.isEmpty(cls.getAnnotations())) return;
		
		if(cls.isAnnotationPresent(Bean.class)) {
			Bean beanAnno = cls.getAnnotation(Bean.class);
			if(beanAnno.value() != "") {
				register(beanAnno.value(), cls);
				return;
			}
		}	
		register(cls);
		if(cls.isAnnotationPresent(Hook.class)) {
			String[] paths = cls.getAnnotation(Hook.class).value();
			Stream.of(paths).forEach(path -> routeManager.addWebHook(cls, path, ioc.getBean(cls.getName())));
		}else if(cls.isAnnotationPresent(Controller.class)) {
			routeManager.addRoute(cls, ioc.getBean(cls.getName()));
		}else if(cls.isAnnotationPresent(Aop.class)) {
			aspectManager.addAspect(cls, ioc.getBean(cls.getName()));
		}
	}
	
	public Sparrow get(String path, RouteHandler handler) {
		routeManager.addRoute(path, handler, HttpMethod.GET);
		return this;
	}
	
	public Sparrow post(String path, RouteHandler handler) {
		routeManager.addRoute(path, handler, HttpMethod.POST);
		return this;
	}
	
	public Sparrow addPackages(String... packages) {
		this.packages.addAll(Arrays.asList(packages));
		return this;
	}
	
	public Sparrow addConfig(String config) {
		this.configs.add(config);
		return this;
	}
	
	public Sparrow start() {
		init();
		new Thread(() -> {
			try {
				server = JettyServer.instance(Sparrow.this);
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		return this;
	}
	
	public void await() {
		try {
			latch.await();
			System.out.println("await...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		server.stop();
	}
}
