package com.sparrow.proxy;

import org.junit.Test;

import com.test.common.Hello;
import com.test.common.HelloImpl;

public class JdkProxyMangerTest {

	@Test
	public void testNewInstance() {
		
		Hello h = new HelloImpl();
		
		Hello proxy = (Hello) JdkProxyManager.newProxy(h);
		proxy.sayHello();
	}
}
