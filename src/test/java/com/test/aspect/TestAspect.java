package com.test.aspect;

import java.lang.reflect.Method;

import com.sparrow.aop.Aspect;
import com.sparrow.aop.annotation.Aop;

@Aop("com.*")
public class TestAspect implements Aspect{

	@Override
	public void before(Object proxy, Method method, Object[] args) {
		System.out.println("testAspect before : " + method.getName());
	}

	@Override
	public void after(Object proxy, Method method, Object[] args, Object result) {
		System.out.println("testAspect after : " + method.getName() + " result :" + result);
	}
	
}
