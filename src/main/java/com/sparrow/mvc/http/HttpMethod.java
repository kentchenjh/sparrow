package com.sparrow.mvc.http;

public enum HttpMethod {
	
	GET,
	POST,
	PUT,
	DELETE,
	BEFORE,
	AFTER,
	ALL,
	;
	
	public static HttpMethod match(String method) {
		
		for(HttpMethod m : HttpMethod.values()) {
			if(m.name().equals(method)) {
				return m;
			}
		}
		return null;
	}
}
