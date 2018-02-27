package com.test.bean;

import com.sparrow.anno.Autowired;
import com.sparrow.anno.Bean;
import com.test.controller.TestController;

@Bean
public class Component {

	@Autowired
	TestController controller;

	@Override
	public String toString() {
		return "Component [controller=" + controller + "]";
	}
	
}
