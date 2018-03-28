package com.sparrow.proxy;

import java.lang.reflect.Method;
import java.util.List;

import com.sparrow.aop.Aspect;
import com.sparrow.aop.AspectManager;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

@Data
public class AspectChain {

	private List<Aspect> aspects;
	
	private int index = 0;
	
	private Object target;
	
	private Method method;
	
	private Object[] args;
	
	private MethodProxy proxy;
	
	private Class<?> targetClazz;
	
	public AspectChain(AspectManager aspectManager, Object target, Method method,
					Object[] args) {
		this(aspectManager, target, method, args, null, target.getClass());
	}
	
	public AspectChain(AspectManager aspectManager, Object target, Method method,
			Object[] args, MethodProxy proxy, Class<?> targetClazz) {
		this.target = target;
		this.method = method;
		this.args = args;
		this.proxy = proxy;
		this.targetClazz = targetClazz;
		this.aspects = initAspectList(aspectManager);
	}
	
	private List<Aspect> initAspectList(AspectManager aspectManager) {
		
		List<Aspect> aspects = null;
		String  fullName = targetClazz.getName() + "." + this.method.getName();
		aspects = aspectManager.getAspect(fullName);
		return aspects;
	}
	
	public Object doChain() throws Throwable {
		Object result = null;
		if(index < aspects.size()) {
			result = aspects.get(index++).doAspect(this);
		} else {
			if(proxy != null) {
				result = proxy.invokeSuper(target, args);
			} else {
				result = method.invoke(target, args);
			}
		}
		return result;
	}
	
}
