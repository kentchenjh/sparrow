package com.sparrow.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TestMethodInterceptor implements MethodInterceptor{

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("cglib intercepts method begins : " + method.getName());
		System.out.println();
		Object result = proxy.invokeSuper(target, args);
		System.out.println("cglib intercepts method ends : " + method.getName());
		return result;
	}

}
