package com.sparrow.kit;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.sparrow.Const;

import static org.junit.Assert.*;

public class IOKitTest {

	@Test
	public void testReadToString() {
		
//		System.out.println(Const.CLASS_PATH + "\\abc.txt");
		
		try {
			String body = IOKit.readToString(Const.CLASS_PATH + File.separator +"abc.txt");
			assertNotNull(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
