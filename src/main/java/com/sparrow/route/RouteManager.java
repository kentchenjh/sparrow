package com.sparrow.route;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparrow.kit.CollectionKit;
import com.sparrow.kit.ReflectKit;
import com.sparrow.kit.RegexKit;
import com.sparrow.mvc.annotation.Path;
import com.sparrow.mvc.http.HttpMethod;

public class RouteManager {

	private Map<String, Route> routes = new HashMap<>();
	
	private Map<String, List<Route>> hooks = new HashMap<>();
	
	private static final String HASH_KEY = "#";
	
	private static final String METHOD_NAME = "handle";
	
	private static final String BEFORE_METHOD_NAME = "before";
	
	private static final String AFTER_METHOD_NAME = "after";
	
	public void addWebHook(Class<?> cls, String path, Object target) {
		
		Method before = ReflectKit.getMethod(cls, BEFORE_METHOD_NAME);
		Method after = ReflectKit.getMethod(cls, AFTER_METHOD_NAME);
		
		Route beforeRoute = new Route(path, HttpMethod.BEFORE, target, before, cls);
		Route afterRoute = new Route(path, HttpMethod.AFTER, target, after, cls);

		if(null != before) {
			addWebHook(generateKey(path, HttpMethod.BEFORE), beforeRoute);
		}
		if(null != after) {
			addWebHook(generateKey(path, HttpMethod.AFTER), afterRoute);
		}
	}
	
	private void addWebHook(String key, Route route) {
		
		List<Route> routes = hooks.get(key);
		if(CollectionKit.isEmpty(routes)) {
			routes = new ArrayList<>();
			hooks.put(key, routes);
		}
		routes.add(route);
		
	}

	public boolean hasBeforeHook() {
		return hooks.keySet().stream().filter(name -> name.endsWith(HttpMethod.BEFORE.name().toUpperCase()))
				.count() > 0;
	}
	
	public boolean hasAfterHook() {
		return hooks.keySet().stream().filter(name -> name.endsWith(HttpMethod.AFTER.name().toUpperCase()))
				.count() > 0;
	}
	
	public void addRoute(String path, Object target, Method action, HttpMethod method, Class<?> targetType) {
		routes.put(generateKey(path, method), new Route(path, method, target, action, targetType));
	}
	
	public void addRoute(Class<?> cls, Object bean) {
		
		if(null == bean) return;
		List<Method> methods = ReflectKit.getAnnoatedMethods(cls, Path.class);
		if(!CollectionKit.isEmpty(methods)) {
			for(Method m : methods) {
				String path = m.getAnnotation(Path.class).value();
				HttpMethod type = m.getAnnotation(Path.class).type();
				addRoute(path, bean, m, type, cls);
			}
		}
	}
	
	public Route findRoute(String path, HttpMethod method) {
		
		String key = generateKey(path, method);
		Route route = routes.get(key);
		if(null == route){
			return routes.get(generateKey(path, HttpMethod.ALL));
		}
		return route;
	}
	
	public List<Route> findHook(String path, HttpMethod method) {
		
		return hooks.values().stream().flatMap(Collection::stream)
				.filter(route -> route.getMethod().equals(method) && matchRoute(route, path))
				.collect(Collectors.toList());
	}
	
	private static boolean matchRoute(Route route, String path) {
		String routePath = RegexKit.pathToRegex(route.getPath());
		return RegexKit.match(routePath, path);
	}
	
	public String generateKey(String path, HttpMethod method) {
		return path + HASH_KEY + method.name();
	}

	public void addRoute(String path, RouteHandler handler, HttpMethod httpMethod) {
		Class<?> clazz = handler.getClass();
		Method action;
		try {
			action = clazz.getMethod(METHOD_NAME, HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {
			return;
		}
		addRoute(path, handler, action, httpMethod, RouteHandler.class);
	}

}
