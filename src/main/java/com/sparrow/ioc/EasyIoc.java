package com.sparrow.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sparrow.kit.ReflectKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EasyIoc implements Ioc {

	private final Map<String, ClassInfo> container = new HashMap<>();

	@Override
	public <T> T getBean(String name) {
		ClassInfo clazz = container.get(name);
		if(null == clazz) return null;
		
		Object obj = clazz.getInstance();
		if(null == obj) {
			obj = ReflectKit.instance(clazz.getClazz());
			if(null == obj) return null;
			clazz.setInstance(obj);
		}
		return (T) obj;
	}

	@Override
	public void addBean(String name, Object bean) {
		ClassInfo clzInfo = new ClassInfo(bean);
		addBean(name, clzInfo);
	}

	@Override
	public void addBean(ClassInfo clazzInfo) {
		addBean(clazzInfo.getClazz().getName(), clazzInfo);
	}
	
	private void initBean(ClassInfo clazzInfo) {
		if(clazzInfo.getInstance() == null) {
			Object instance = ReflectKit.instance(clazzInfo.getClazz());
			clazzInfo.setInstance(instance);
		}
	}

	@Override
	public void addBean(String name, ClassInfo clazzInfo) {
		initBean(clazzInfo);
		if(null != container.put(name, clazzInfo)){
			log.warn("duplicate bean: {}", clazzInfo.getClazz());
		};
	}

	@Override
	public List<ClassInfo> getClassInfos() {
		return new ArrayList<>(container.values());
	}

	@Override
	public List<Object> getBeans() {
		
		return container.values().stream()
				.map(ClassInfo::getInstance)
				.collect(Collectors.toList());
	}
	
}

