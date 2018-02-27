package com.sparrow;

import java.util.regex.Pattern;

import org.junit.Test;

import com.sparrow.mvc.handler.StaticsHandler;

public class ConstTest {

	public static void main(String[] args) {
		System.out.println(StaticsHandler.class.getPackage().getName() + "." + StaticsHandler.class.getSimpleName());
	}
	
	@Test
	public void fukc() {
		String routePath = "\\/.*";
		Pattern pattern = Pattern.compile(routePath);
		System.out.println(pattern.matcher("/home").matches());
		
		int i = 0;
		String str = "sfsda|a";
		for(String s : str.split("\\|")) {
			System.out.println(s + i++);
		}
	}
}
