package com.sparrow.proxy;

import org.junit.Test;

import com.sparrow.aop.AspectManager;
import com.test.aspect.TestAspect;
import com.test.aspect.TestServiceAspect;
import com.test.common.Hello;
import com.test.common.HelloImpl;
import com.test.service.NonInterfaceService;

public class CglibProxyManagerTest {

	@Test
	public void testNewInstance() {
		
		Hello h = new HelloImpl();
		Hello proxy = (Hello) CglibProxyManger.newProxy(h, new TestMethodInterceptor());
		proxy.sayHello();
	}
	
	@Test
	public void testAopProxy() {
		AspectManager m = new AspectManager();
		TestAspect ta = new TestAspect();
		TestServiceAspect tsa = new TestServiceAspect();
		
		m.addAspect(TestAspect.class, ta);
		m.addAspect(TestAspect.class, tsa);
		
		NonInterfaceService proxy = (NonInterfaceService) CglibProxyManger.newAopProxy(new NonInterfaceService(), m);
		proxy.insertUser();
	}
}
