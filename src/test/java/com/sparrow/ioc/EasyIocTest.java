package com.sparrow.ioc;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.test.controller.TestController;
import com.test.service.TestService;

import static org.junit.Assert.*;

public class EasyIocTest {

	static Ioc ioc;

	@Before
	public void initIoc() {
		ioc = new EasyIoc();
		ioc.addBean("testController", new TestController());
		ioc.addBean("testService", new TestService());
	}
	
	
	
	@Test
	public void testAddBean() {
		
	}
	
	@Test
	public void testGetBeans() {
		List<Object> objs = ioc.getBeans();
		assertNotNull(objs);
		assertTrue(objs.size() > 0);
	}
}
