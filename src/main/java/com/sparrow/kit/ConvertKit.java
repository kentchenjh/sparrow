package com.sparrow.kit;

public class ConvertKit {

	public static int String2Int(String str) {
		return Integer.parseInt(str);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Object cast(Object origin, T targetClz) {
		return (T) origin;
	}
}
