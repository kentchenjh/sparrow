package com.sparrow.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.sparrow.aop.AspectManager;

public class JdkProxyManager {
	
	@SuppressWarnings("unchecked")
	public static <T> T newProxy(Object target, InvocationHandler handler) {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), handler);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newAopProxy(Object target, AspectManager aspectManager) {
		return (T) newProxy(target, new AopInvocationHandler(target, aspectManager));
	}
}
