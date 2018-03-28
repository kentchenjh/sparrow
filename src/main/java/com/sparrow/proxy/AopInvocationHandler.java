package com.sparrow.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.sparrow.aop.AspectManager;

public class AopInvocationHandler implements InvocationHandler{

	private Object target;
	
	private AspectManager aspectManager;
	
	public AopInvocationHandler(Object target, AspectManager aspectManager) {
		this.target = target;
		this.aspectManager = aspectManager;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return new AspectChain(aspectManager, target, method, args).doChain();
	}

}
