package com.test.bean;

import com.sparrow.anno.Autowired;
import com.sparrow.anno.Bean;
import com.sparrow.anno.Value;
import com.test.controller.TestController;

import lombok.Data;

@Bean("component")
@Data
public class Component {

	@Value(name="app.version")
	String version;
	
	@Autowired
	TestController controller;
	
}
