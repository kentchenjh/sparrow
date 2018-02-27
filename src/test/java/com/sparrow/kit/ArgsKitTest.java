package com.sparrow.kit;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.sparrow.route.RouteHandler;

public class ArgsKitTest {

	@Test
	public void testParamNames() throws NoSuchMethodException, SecurityException {
		
		
		Class clz1 = RouteHandlerTest.class;
		Class[] types = {HttpServletRequest.class, HttpServletResponse.class};
		String[] names = AsmKit.getParamNamesByMethod(clz1.getDeclaredMethod("handle", types));
		System.out.println(Arrays.toString(names));
		
	}
	
	static class RouteHandlerTest implements RouteHandler {

		@Override
		public void handle(HttpServletRequest r, HttpServletResponse rp) {
			
		}
		
	}
	
	public void testMethod1(int arg1, String str, long num) {
		
	} 
}
