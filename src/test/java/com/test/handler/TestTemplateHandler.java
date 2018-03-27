package com.test.handler;

import com.sparrow.ioc.annotation.Bean;
import com.sparrow.mvc.template.DefaultTemplateEngine;

@Bean("templateEngine")
public class TestTemplateHandler extends DefaultTemplateEngine{

	public TestTemplateHandler() {
		this.prefix = "/template";
		this.suffix = ".spa";
	}
	
}
