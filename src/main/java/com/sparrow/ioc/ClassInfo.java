package com.sparrow.ioc;

import lombok.Data;

@Data
public class ClassInfo {

	private Object instance;
	
	private Class<?> clazz;
	
	public ClassInfo(Object instance) {
		this.clazz = instance.getClass();
		this.instance = instance;
	}
	
	public ClassInfo(Class<?> clazz) {
		this.clazz = clazz;
	}
	
}
