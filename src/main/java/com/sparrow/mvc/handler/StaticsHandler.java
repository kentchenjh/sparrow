package com.sparrow.mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StaticsHandler {

	public void hanlde(String path, HttpServletRequest request, HttpServletResponse response);
}
