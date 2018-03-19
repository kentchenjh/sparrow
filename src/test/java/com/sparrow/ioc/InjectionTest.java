package com.sparrow.ioc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sparrow.Sparrow;

public class InjectionTest {

	private Sparrow sparrow;
	
	@Before
	public void before() {
		
		sparrow = Sparrow.me();
		sparrow.addConfig("app.properties").start().await();
	}
	
	@Test
	public void testValueInjection() {
		
		assertNotNull(sparrow.getIoc().getBean("component"));
		System.out.println(sparrow.getIoc().getBean("component").toString());
	}
	
}
