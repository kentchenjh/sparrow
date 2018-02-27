package com.sparrow.ioc;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.sparrow.anno.Bean;
import com.sparrow.kit.FileKit;
import com.sparrow.kit.ReflectKit;

public class Scanner {

	private static final String JAR_SUFFIX = ".jar";
	private static final String CLASS_SUFFIX = ".class";
	
	
	public static Set<ClassInfo> scan(String packageName) {
	
		Set<ClassInfo> classInfos = new HashSet<>();
		
		String root = FileKit.getPath(packageName);
		try {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(root);
			while(urls.hasMoreElements()) {
				URL dir = urls.nextElement();
				String path = new URI(dir.getFile()).getPath();
				findClassByPackage(packageName, path, classInfos);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classInfos;
		
	}

	private static Collection<? extends ClassInfo> findClassByPackage(String packageName, String path, Set<ClassInfo> classInfos) throws ClassNotFoundException {
		
		File root = new File(path);
		File[] files = root.listFiles();
		for(File f : files) {
			
			if(f.isDirectory()) {
				findClassByPackage(packageName + "." + f.getName(), f.getAbsolutePath(), classInfos);
			}
			
			if(f.getName().contains(JAR_SUFFIX)){
				//TODO
			}
			
			if(f.getName().contains(CLASS_SUFFIX)) {
				String clazzName =  f.getName().substring(0, f.getName().length() - CLASS_SUFFIX.length());
				Class<?> clazz = Class.forName(packageName + "." + clazzName);
				if(ReflectKit.hasAnnotation(Bean.class, clazz)) {
					classInfos.add(new ClassInfo(clazz));
				}
			}
		}
		return classInfos;
	}
	
	
}
