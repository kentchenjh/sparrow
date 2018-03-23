package com.sparrow;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public class SparrowTest {

	@After
	public void after() {
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStart() {
//		Sparrow.me().addPackages("com.test").start();
//		
		Sparrow.me().addConfig("app.properties").start().await();
		
//		Sparrow.me().get("/", new RouteHandler() {
//			
//			@Override
//			public void handle(HttpServletRequest request, HttpServletResponse response) {
//				PrintWriter writer = null;
//				try {
//					writer = response.getWriter();
//					writer.write("welcome");
//				} catch (IOException e) {
//					e.printStackTrace();
//					IOKit.closeQuietly(writer);
//				}
//			}
//		}).start();
	}
	
//	@Test
	public void testConfig() {
		
		String config = "handlers.properties";
		Sparrow me = Sparrow.me();
		me.addConfig(config);
		
		assertTrue(me.getConfigs().contains(config));
	}
	
}
