package com.sparrow.proxy;

import org.junit.Test;

import com.sparrow.aop.AspectManager;
import com.test.aspect.TestAspect;
import com.test.aspect.TestServiceAspect;
import com.test.common.Hello;
import com.test.common.HelloImpl;
import com.test.service.TestService;
import com.test.service.TestServiceI;

public class JdkProxyMangerTest {

	@Test
	public void testNewInstance() {
		
		Hello h = new HelloImpl();
		Hello proxy = (Hello) JdkProxyManager.newProxy(h, new TestInvocationHandler(h));
		proxy.sayHello();
	}
	
	@Test 
	public void testAopProxy() {

		AspectManager m = new AspectManager();
		TestAspect ta = new TestAspect();
		TestServiceAspect tsa = new TestServiceAspect();
		
		m.addAspect(TestAspect.class, ta);
		m.addAspect(TestAspect.class, tsa);
		
		
		TestServiceI proxy = (TestServiceI) JdkProxyManager.newAopProxy(new TestService(), m);
		proxy.insertUser();
	}
}
