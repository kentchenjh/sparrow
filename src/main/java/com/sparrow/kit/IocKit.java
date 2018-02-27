package com.sparrow.kit;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sparrow.anno.Autowired;
import com.sparrow.ioc.ClassInfo;
import com.sparrow.ioc.Ioc;
import com.sparrow.ioc.Scanner;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IocKit {

	public static void injection(Ioc ioc, ClassInfo classInfo) {
		
		if(null == classInfo || null == classInfo.getInstance()) return;
		
		List<Field> fields = ReflectKit.getAnnonatedFields(classInfo.getClazz(), Autowired.class);
		Object target = classInfo.getInstance();
		if(!CollectionKit.isEmpty(fields)) {
			fields.stream().forEach(f -> {
				Object toInjected = ioc.getBean(f.getType().getName());
				if(null != toInjected) {
					ReflectKit.injectField(target, f, toInjected);
				}
			});
		}
	}

	public static void scanBeans(Ioc ioc, String _package) {
		Set<ClassInfo> clazzSet = Scanner.scan(_package);
		if(CollectionKit.isEmpty(clazzSet)) return;
		for(ClassInfo clzInfo : clazzSet) {
			ioc.addBean(clzInfo);
		}
	}
	
	public static List<ClassInfo> getClassInfoByAnnotation(Ioc ioc, Class anno) {
		
		return ioc.getClassInfos().stream()
					.filter( clzInfo -> clzInfo.getClazz().isAnnotationPresent(anno))
					.collect(Collectors.toList());	
	}
	
}