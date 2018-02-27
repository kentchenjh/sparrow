package com.sparrow.kit.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionKit {

	public static Map<String, Object> getParamsFromSession(HttpSession session) {
		
		Map<String, Object> attributes = new HashMap<>();
		if(null == session) return attributes;
		
		Enumeration<String> attrNames = session.getAttributeNames();
		if(attrNames.hasMoreElements()) {
			String key = attrNames.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		return attributes;
	}
}
