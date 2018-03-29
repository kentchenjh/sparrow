package com.sparrow.mvc.request;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparrow.kit.JsonKit;
import com.sparrow.kit.ReflectKit;
import com.sparrow.kit.web.ResponseKit;
import com.sparrow.mvc.ModelAndView;
import com.sparrow.mvc.Signature;
import com.sparrow.mvc.WebContext;
import com.sparrow.mvc.adapter.RouteAdapter;
import com.sparrow.mvc.annotation.JSON;
import com.sparrow.mvc.handler.ExceptionHandler;
import com.sparrow.mvc.handler.StaticsHandler;
import com.sparrow.mvc.http.HttpMethod;
import com.sparrow.route.Route;
import com.sparrow.route.RouteHandler;
import com.sparrow.route.RouteManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestExecution {

	private String path;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private final static RouteManager ROUTE_MANAGER 		= WebContext.sparrow().getRouteManager();
	private final static Set<String> STATICS 				= WebContext.sparrow().getStatics();
//	private final static StaticsHandler STATICS_HANDLER 	= WebContext.sparrow().getStaticsHandler();
	private final static ExceptionHandler EXCEPTION_HANDLER = WebContext.sparrow().getExceptionHandler();
	private final static List<RouteAdapter> ROUTE_ADAPTERS  = WebContext.sparrow().getRouteAdapters();
	private final static boolean hasBeforeHook 				= WebContext.sparrow().getRouteManager().hasBeforeHook();
	private final static boolean hasAfterHook 				= WebContext.sparrow().getRouteManager().hasAfterHook();
	
	
	public RequestExecution(HttpServletRequest req, HttpServletResponse resp) {
		
		this.path = req.getServletPath();
		this.request = req;
		this.response = resp;
	}
	
	public void process() {
		
		WebContext.set(new WebContext(request, response));
		
		HttpMethod method = HttpMethod.match(request.getMethod());
		Route route = ROUTE_MANAGER.findRoute(path, method);
		Signature signature = new Signature(request, response, route);
		
		try {
			if(null == route) {
				log.info("can not found route by path {}", path);
				noRouteFound(signature);
				return;
			}
			//before hook
			if(hasBeforeHook && !invokeHook(path, signature, HttpMethod.BEFORE)) {
				return;
			}
			
			RouteAdapter routeAdapter = getAdapter(signature.getRoute());
			Object result = routeAdapter.handle(signature);
			
			//after hook
			if(hasAfterHook && !invokeHook(path, signature, HttpMethod.AFTER)) {
				return;
			}
			
			//handle result
			this.processResult(signature, result);
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) e = (Exception) e.getCause();
			e.printStackTrace();
			EXCEPTION_HANDLER.handleException(e, signature);
		}
	}
	
	
	private void noRouteFound(Signature signature) {
		String errorMsg = "no route find for " + path;
		try {
			ResponseKit.sendError(signature.getResponse(), errorMsg);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(errorMsg);
		}
	}

	private RouteAdapter getAdapter(Route route) {
		
		for(RouteAdapter adapter : ROUTE_ADAPTERS) {
			if(adapter.support(route)) {
				return adapter;
			}
		}
		throw new RuntimeException("no adapter for " + route);
	}

	private boolean invokeHook(String path, Signature signature, HttpMethod httpMethod) throws Exception {

		List<Route> hooks = ROUTE_MANAGER.findHook(path, httpMethod);
		for(Route hook : hooks) {
			Object[] params = {signature};
			Object result = ReflectKit.invoke(hook.getTarget(), hook.getAction(), params);
			if(!(boolean) result) {
				return false;
			}
		}
		return true;
	}


	private void processResult(Signature signature, Object result) throws Exception{
		
		Route route = signature.getRoute();
		boolean isJson = route.getAction().getAnnotation(JSON.class) != null;
		Class<?> returnType = route.getAction().getReturnType();
		HttpServletResponse response= signature.getResponse();
		
		if(null == result) return;
		
		if(isJson) {
			String json = JsonKit.obj2Str(result);
			response.getWriter().write(json);
			return;
		}
		
		if(returnType == String.class) {
			WebContext.sparrow().getTemplateEngine().render(new ModelAndView(result.toString()));
			return;
		}
		
		if(returnType == ModelAndView.class) {
			ModelAndView mav = (ModelAndView) result;
			WebContext.sparrow().getTemplateEngine().render(mav);
			return;
		}
	}

}
