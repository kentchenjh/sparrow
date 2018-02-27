package com.sparrow;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.sparrow.kit.IOKit;
import com.sparrow.route.RouteHandler;

public class SparrowTest {

	@Test
	public void testStart() {
//		Sparrow.me().addPackages("com.test").start();
//		
//		Sparrow.me().addConfig("app.properties").start();
		
		
		Sparrow.me().get("/", new RouteHandler() {
			
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response) {
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
					writer.write("welcome");
				} catch (IOException e) {
					e.printStackTrace();
					IOKit.closeQuietly(writer);
				}
			}
		}).start();
	}
	
//	@Test
	public void testConfig() {
		
		String config = "handlers.properties";
		Sparrow me = Sparrow.me();
		me.addConfig(config);
		
		assertTrue(me.getConfigs().contains(config));
	}
	
}
