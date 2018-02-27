package com.sparrow.ioc;

import java.util.Set;

import org.junit.Test;

public class ScannerTest {
	
	@Test
	public void testScan() throws InstantiationException, IllegalAccessException {
		
		Scanner scanner = new Scanner();
		Set<ClassInfo> classes = scanner.scan("com.test");
//			for(ClassInfo ci : classes) {
//				System.out.println(ci.getClazz().newInstance());
//			}
		System.out.println(classes);
	}
}
