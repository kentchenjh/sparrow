package com.sparrow.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparrow.Sparrow;

public class WebContext {

	private static ThreadLocal<WebContext> webContexts = new ThreadLocal<>();
	
	private static Sparrow sparrow;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public WebContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public static void init(Sparrow s) {
		sparrow = s;
	}
	
	public static Sparrow sparrow() {
		return sparrow;
	}
	
	public static void set(WebContext webContext)  {
		webContexts.set(webContext);
	}
	
	public static HttpServletRequest request() {
		return webContexts.get().request;
	}
	
	public static HttpServletResponse response() {
		return webContexts.get().response;
	}
}
