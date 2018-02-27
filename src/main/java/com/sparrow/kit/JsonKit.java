package com.sparrow.kit;

import com.alibaba.fastjson.JSON;

public class JsonKit {

	public static String obj2Str(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	public static Object str2Obj(String str, Class<?> clazz) {
		return JSON.parseObject(str, clazz);
	}
	
}
