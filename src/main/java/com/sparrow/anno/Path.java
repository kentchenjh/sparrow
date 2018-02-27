package com.sparrow.anno;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sparrow.mvc.http.HttpMethod;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Path {

	String value() default "/";
	
	HttpMethod type() default HttpMethod.ALL;
}
