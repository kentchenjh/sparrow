package com.sparrow.kit;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class ReflectKit {

	public static Object invoke(Object object, Method method, Object[] params) {
		
		if(null == object || null == method) return null;
		
		method.setAccessible(true);
		Object result;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	public static Class<?> getClassByFullName(String fullName) {
		
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(fullName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * get an instance for the full name which stands for a class
	 * 
	 * full name for example :
	 * com.sparrow.mvc.adapter.RouteHandlerAdapter
	 * 
	 * @param fullName
	 * @return
	 */
	public static Object getInstanceByFullName(String fullName) {
		return instance(getClassByFullName(fullName));
	}
	
	public static Object instance(Class<?> clz) {
		
		try {
			return clz.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean isPrimitive(Class<?> clz) {
		return clz == byte.class
				|| clz == Byte.class
				|| clz == char.class
				|| clz == Character.class
				|| clz == short.class
				|| clz == Short.class
				|| clz == int.class
				|| clz == Integer.class
				|| clz == double.class
				|| clz == Double.class
				|| clz == float.class
				|| clz == Float.class
				|| clz == long.class
				|| clz == Long.class
				|| clz == boolean.class
				|| clz == Boolean.class
				;
	}
	
	/**
	 * check if this class has target annotation or its 
	 * annotations contain the target.
	 * @param targetAnno
	 * @param clazz
	 * @return
	 */
	public static boolean hasAnnotation(Class<?> targetAnno, Class<?> clazz) {
		
		if(!targetAnno.isAnnotation()) return false;
		
		Annotation[] annos = clazz.getAnnotations();
		if(null != annos && annos.length > 0) {
			for(Annotation anno : annos) {
				if(anno.annotationType() == Retention.class || 
				   anno.annotationType() == Target.class ||
				   anno.annotationType() == Documented.class) continue;
				if(anno.annotationType() == targetAnno || hasAnnotation(targetAnno, anno.annotationType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * get full class name like this:
	 * input : 
	 * StaticsHandler.class
	 * output :
	 * com.sparrow.mvc.handler.StaticsHandler
	 * 
	 * @param _class 
	 * @return
	 */
	public static String fullClassName(Class<?> _class) {
		return _class.getPackage().getName() + "." + _class.getSimpleName();
	}
	
	public static void injectField(Object obj, String filedName, Object value) {
		
		Class<?> clazz = obj.getClass();
		try {
			Field field = clazz.getDeclaredField(filedName);
			injectField(obj, field, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void injectField(Object obj, Field field, Object value) {
		
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * return the declared fields which contains specific annotation 
	 * 
	 * @param clazz
	 * @param anno
	 * @return
	 */
	public static List<Field> getAnnonatedFields(Class<?> clazz, Class anno) {
		
		List<Field> annoatedFileds = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for(Field f : fields) {
			f.setAccessible(true);
			if(f.isAnnotationPresent(anno)) {
				annoatedFileds.add(f);
			}
		}
		return annoatedFileds;
	}
	
	public static List<Method> getAnnoatedMethods(Class<?> clazz, Class anno) {
		
		List<Method> annoatedMethods = new ArrayList<>();
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m : methods) {
			m.setAccessible(true);
			if(m.isAnnotationPresent(anno)) {
				annoatedMethods.add(m);
			}
		}
		return annoatedMethods;
	}
	
	public static boolean hasInterface(Class<?> clz, Class<?> _interface) {
		return Stream.of(clz.getInterfaces()).filter(i -> i.equals(_interface)).count() > 0;
	}
	
	public static Method getMethod(Class<?> clz, String methodName) {
		
		return Stream.of(clz.getDeclaredMethods()).filter(m -> m.getName().equals(methodName))
				.findFirst().orElse(null);
	}
} 
