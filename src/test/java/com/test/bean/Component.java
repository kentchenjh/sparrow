package com.test.bean;

import com.sparrow.ioc.annotation.Autowired;
import com.sparrow.ioc.annotation.Bean;
import com.sparrow.ioc.annotation.Value;
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
