package com.sparrow.kit.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.sparrow.kit.IOKit;

public class ResponseKit {

	public static void sendError(HttpServletResponse response, String msg) throws IOException {
		
		response.getWriter().write("<html><body>" + msg + "</body></html>");
		IOKit.closeQuietly(response.getWriter());
	}
	
}
