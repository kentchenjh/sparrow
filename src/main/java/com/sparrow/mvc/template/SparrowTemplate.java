package com.sparrow.mvc.template;

import java.util.HashMap;
import java.util.Map;

import static com.sparrow.mvc.template.CharState.*;

public class SparrowTemplate {

	private String content;
	
	private Map<String, Object> attributes = new HashMap<>();
	
	public static SparrowTemplate init(String content, Map<String, Object> attributes) {
		SparrowTemplate parser = new SparrowTemplate();
		parser.content = content;
		parser.attributes.putAll(attributes);
		return parser;
	}
	
	public String parse() {
		
		int i = 0;
		CharState state = FREE_TEXT;
		StringBuilder sb = new StringBuilder();
		StringBuilder attr = new StringBuilder();
		
		while(i < content.length()) {
			char chr = content.charAt(i);
			state = nextState(state, i);
			switch (state) {
				case FREE_TEXT:
					sb.append(chr);
					break;
				case PARAM_START:
					i++;
					break;
				case PARAM:
					attr.append(chr);
					break;
				case PARAM_END:
					sb.append(getAttribute(attr.toString()));
					attr = new StringBuilder();
					break;
				case ESPACE:
					break;
					
				default:
					break;
			}
			i++;
		}
		
		return sb.toString();
	}

	private Object getAttribute(String key) {
		
		if(null != key) {
			return attributes.get(key);
		}
		return null;
	}

	private CharState nextState(CharState state, int i) {
		
		switch(state) {
			case FREE_TEXT:
				return fromFreeText(content, i);
			case PARAM:
				return fromParam(content, i);
			case PARAM_START:
				return fromParamStart(content, i);
			case PARAM_END:
				return fromParamEnd(content, i);
			case ESPACE:
				return FREE_TEXT;
		}
		
		return null;
	}


	private CharState fromParamEnd(String str, int i) {
		if(isParamStart(str, i)) {
			return PARAM_START;
		}
		if(isEspace(str, i)) {
			return ESPACE;
		}
		return FREE_TEXT;
	}

	private static CharState fromParamStart(String str, int i) {
		if(isParamEnd(str, i)) {
			return PARAM_END;
		}
		return PARAM;
	}

	private static CharState fromParam(String str, int i) {
		if(isParamEnd(str, i)) {
			return PARAM_END;
		}
		return PARAM;
	}

	private static boolean isParamEnd(String str, int i) {
		return '}' == str.charAt(i);
	}

	private static CharState fromFreeText(String str, int i) {
		if(isEspace(str, i)) {
			return ESPACE;
		}
		if(isParamStart(str, i)) {
			return PARAM_START;
		}
		return FREE_TEXT;
	}

	private static boolean isParamStart(String str, int i) {
		return '$' == str.charAt(i) && i+1 < str.length() && '{' == str.charAt(i+1);
	}

	private static boolean isEspace(String str, int i) {
		return '`' == str.charAt(i);
	}

	
}
