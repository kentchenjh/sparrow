package com.sparrow.kit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sparrow.anno.Controller;
import com.sparrow.ioc.ClassInfo;
import com.sparrow.ioc.EasyIoc;
import com.sparrow.ioc.Ioc;
import com.test.controller.TestController;
import com.test.service.TestService;

public class IocKitTest {

	static Ioc ioc;

	@Before
	public void initIoc() {
		ioc = new EasyIoc();
		ioc.addBean("testController", new TestController());
		ioc.addBean("testService", new TestService());
	}
	
	@Test
	public void testGetBeansByAnnotation() {
		
		List<ClassInfo> beans = IocKit.getClassInfoByAnnotation(ioc, Controller.class);
		
		assertNotNull(beans);
		assertTrue(beans.size() > 0);
		assertTrue(TestController.class == beans.get(0).getClazz());
	}
	
}
