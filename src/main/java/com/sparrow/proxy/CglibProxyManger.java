package com.sparrow.proxy;

import com.sparrow.aop.AspectManager;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibProxyManger {

	@SuppressWarnings("unchecked")
	public static <T> T newProxy(T target, MethodInterceptor interceptor) {
		
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(interceptor);
		return (T) enhancer.create();
	}

	public static <T> T newAopProxy(T instance, AspectManager aspectManager) {
		return (T) newProxy(instance, new AopMethodInterceptor(aspectManager, instance));
	}
}
