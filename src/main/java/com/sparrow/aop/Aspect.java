package com.sparrow.aop;

import java.lang.reflect.Method;

import com.sparrow.proxy.AspectChain;

public interface Aspect {

	default void before(Object proxy, Method method, Object[] args) {};
	
	default void after(Object proxy, Method method, Object[] args, Object result) {};
	
	default Object doAspect(AspectChain chain) throws Throwable {
		
		Object target = chain.getTarget();
		Method method = chain.getMethod();
		Object[] args = chain.getArgs();
		
		Object result = null;
		try {
			before(target, method, args);
			result = chain.doChain();
			after(target, method, args, result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}
