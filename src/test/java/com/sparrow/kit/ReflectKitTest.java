package com.sparrow.kit;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import com.sparrow.anno.Autowired;
import com.sparrow.ioc.Father;

public class ReflectKitTest {

	static class InjectTest {
		
		@Autowired
		String name;
	}
	
	
	@Test
	public void testGetClassByFullName() {
		
		Class<?> clz = ReflectKit.getClassByFullName("com.sparrow.ioc.Father");
		assertEquals(clz, Father.class);
	}
	
	@Test
	public void testInject() {
		
		InjectTest it = new InjectTest();
		String name = "kent";
		
		ReflectKit.injectField(it, "name", name);
		assertEquals(it.name, name);
	}
	
	@Test
	public void testGetAnnotatedField() {
		
		List<Field> fields = ReflectKit.getAnnonatedFields(InjectTest.class, Autowired.class);
		
		assertEquals(fields.size(), 1);
		assertEquals(fields.get(0).getName(), "name");
	}
}
