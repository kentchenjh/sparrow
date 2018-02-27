package com.sparrow.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sparrow.kit.ArgsKit;
import com.sparrow.route.Route;

import lombok.Data;

@Data
public class Signature {

	private static final String LAMBDA = "$$Lambda$";
	
	private Route route;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private Object[] params;
	
	public Signature(HttpServletRequest request, HttpServletResponse response, Route route) {
		this.request = request;
		this.response = response;
		this.route = route;
		
		if (null != this.route && null != this.route.getAction() &&
                !this.route.getAction().getDeclaringClass().getName().contains(LAMBDA)) {
            this.params = ArgsKit.getParams(this);
        }
	}
	
	
}
