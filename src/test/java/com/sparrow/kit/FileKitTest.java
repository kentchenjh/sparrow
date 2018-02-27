package com.sparrow.kit;


import org.junit.Test;
import static org.junit.Assert.*;

public class FileKitTest {

	
	@Test
	public void testGetSuffix() {
		
		String suffix = FileKit.getSuffix("abc.txt");
		assertEquals("txt", suffix);
		
	}
}
