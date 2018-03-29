package com.sparrow.mvc.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.sparrow.kit.IOKit;
import com.sparrow.mvc.Signature;
import com.sparrow.mvc.WebContext;

public class DefaultExceptionHandler implements ExceptionHandler{

	@Override
	public void handleException(Exception e, Signature signature) {
		
		HttpServletResponse response = WebContext.response();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			response.setStatus(500);
			writer.write(e.getMessage() == null ? "unknown reason" : e.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			IOKit.closeQuietly(writer);
		}
	}
}
