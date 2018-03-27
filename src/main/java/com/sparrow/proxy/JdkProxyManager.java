package com.sparrow.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyManager {
	
	public static Object newProxy(Object target) {
		
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("invokes method : " + method.getName());
				Object result = method.invoke(target, args);
				System.out.println("method : " + method.getName() + " ends");
				return result;
			}
		});
	}
	
}
