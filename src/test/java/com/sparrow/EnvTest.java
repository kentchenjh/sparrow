package com.sparrow;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class EnvTest {

	@Test
	public void testOf() {
		
		Set<String> locs = new HashSet<>();
		locs.add("app.properties");
		locs.add("app2.properties");
		
		Env env = Env.empty();
		env.of(locs);
		Assert.assertEquals("kent", env.get("TEST_NAME").get());
		Assert.assertEquals("luck", env.get("TEST_NAME1").get());
		Assert.assertEquals("100", env.get("TEST_NUM").get());
	}
	
}
