package com.sparrow.route;

import java.lang.reflect.Method;

import com.sparrow.mvc.http.HttpMethod;

import lombok.Data;

@Data
public class Route {

	/**
	 *	request path 
	 */
	private String path;
	/**
	 *  http method 
	 *  e.g. GET,POST,DELETE,PUT
	 */
	private HttpMethod method;
	/**
	 *	invoked object 
	 */
	private Object target;
	/**
	 *  the class type of invoekd object
	 */
	private Class<?> targetType;
	/**
	 *  invoked method
	 */
	private Method action;
	
	public Route(String path, HttpMethod method, Object target, Method action, Class<?> targetType) {
		
		this.path = path;
		this.method = method;
		this.target = target;
		this.action = action;
		this.targetType = targetType;
	}
	
	
}
