package com.sparrow.proxy;

import org.junit.Test;

import com.test.common.Hello;
import com.test.common.HelloImpl;

public class CglibProxyManagerTest {

	@Test
	public void testNewInstance() {
		
		Hello h = new HelloImpl();
		Hello proxy = (Hello) CglibProxyManger.newInstance(h);
		proxy.sayHello();
	}
}
