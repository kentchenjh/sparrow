package com.sparrow.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyManger {

	public static Object newInstance(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			
			@Override
			public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("cglib intercepts method begins : " + method.getName());
				Object result = proxy.invokeSuper(target, args);
				System.out.println("cglib intercepts method ends : " + method.getName());
				return result;
			}
		});
		
		return enhancer.create();
	}
}
