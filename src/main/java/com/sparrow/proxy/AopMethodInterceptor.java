package com.sparrow.proxy;

import java.lang.reflect.Method;

import com.sparrow.aop.AspectManager;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AopMethodInterceptor implements MethodInterceptor{

	private AspectManager aspectManager;
	private Object origin;
	
	public AopMethodInterceptor(AspectManager aspectManager, Object instance) {
		this.aspectManager = aspectManager;
		this.origin = instance;
	}
	
	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		return new AspectChain(aspectManager,target, method, args, proxy, origin.getClass()).doChain();
	}

}
