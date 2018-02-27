package com.sparrow.route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RouteHandler {

	public void handle(HttpServletRequest request, HttpServletResponse response);
}
