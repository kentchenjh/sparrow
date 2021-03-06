package com.sparrow.mvc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sparrow.ioc.annotation.Bean;

@Retention(RUNTIME)
@Target(TYPE)
@Bean
public @interface Hook {
	
	String[] value() default {"/*"};
}
