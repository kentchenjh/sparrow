package com.sparrow.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sparrow.aop.annotation.Aop;
import com.sparrow.kit.CollectionKit;
import com.sparrow.kit.RegexKit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

		List<Aspect> aspects = aspectMap.entrySet().stream()
				.filter(e -> {
					log.info("regex : {}", RegexKit.pathToRegex(e.getKey()));
					return RegexKit.match(RegexKit.pathToRegex(e.getKey()), path);
				})
				.map(e -> {
					log.info("match : {}", e.getValue());
					return e.getValue();
				})
				.collect(Collectors.toList());
		
		
		
		return aspects;
	}
}

