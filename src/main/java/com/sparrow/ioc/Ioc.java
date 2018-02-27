package com.sparrow.ioc;

import java.util.List;

public interface Ioc {

	public <T> T getBean(String name);
	
	public void addBean(String name, Object bean);
	
	public void addBean(ClassInfo clazzInfo);
	
	public void addBean(String name, ClassInfo clazzInfo);
	
	public List<ClassInfo> getClassInfos();
	
	public List<Object> getBeans();
}
