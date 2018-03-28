package com.sparrow.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sparrow.aop.annotation.Aop;
import com.sparrow.kit.CollectionKit;
import com.sparrow.kit.RegexKit;

public class AspectManager {

	private Map<String, Aspect> aspectMap = new HashMap<>();
	
	public void addAspect(Class<?> cls, Aspect object) {
		
		String[] paths = cls.getAnnotation(Aop.class).value();
		if(CollectionKit.isEmpty(paths)) return;
		
		Stream.of(paths).forEach(path-> {
			aspectMap.put(path, object);
		});
	}

	public List<Aspect> getAspect(String key) {
		return getAspectByRegex(key);
	}
	
	private List<Aspect> getAspectByRegex(String path) {
		
		return aspectMap.entrySet().stream()
			.filter(e -> RegexKit.match(RegexKit.pathToRegex(e.getKey()), path))
			.map(e -> e.getValue())
			.collect(Collectors.toList());
	}
}

