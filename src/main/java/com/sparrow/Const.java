package com.sparrow;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.sparrow.kit.ReflectKit;
import com.sparrow.mvc.adapter.RouteAdapter;
import com.sparrow.mvc.handler.ExceptionHandler;
import com.sparrow.mvc.handler.StaticsHandler;
import com.sparrow.mvc.template.TemplateEngine;

public interface Const {

	String ENV_KEY_SERVER_ADDRESS 					= "server.address";
	
	String ENV_KEY_SERVER_PORT 						= "server.port";
	
	String DEFAULT_SERVER_ADDRESS 					= "localhost";
	
	String DEFAULT_SERVER_PORT 						= "9000";
	
	String BASE_PACKAGE								= "base.package";
	
	List<String> DEFAULT_STATICS  					= Arrays.asList("/favicon.ico", "/robots.txt", "/static/", "/upload/", "/template/");
	
	String CLASS_PATH 								= new File(Const.class.getResource("/").getPath()).getPath();
	
	List<String> CONFIG_PATH						= Arrays.asList("app.properties");
	
	/*
	 * handlers
	 */
	
	String SYS_STATICS_HANDLER = ReflectKit.fullClassName(StaticsHandler.class);
	
	String SYS_EXCEPTION_HANDLER = ReflectKit.fullClassName(ExceptionHandler.class);
	
	String SYS_TEMPLATE_ENGINE = ReflectKit.fullClassName(TemplateEngine.class);
	
	String SYS_ROUTE_ADAPTER = ReflectKit.fullClassName(RouteAdapter.class);
}
