package com.sparrow;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SparrowTest {

	@Test
	public void testStart() {
		Sparrow.me().addConfig("app.properties").start();
	}
	
//	@Test
	public void testConfig() {
		
		String config = "handlers.properties";
		Sparrow me = Sparrow.me();
		me.addConfig(config);
		
		assertTrue(me.getConfigs().contains(config));
	}
	
}
