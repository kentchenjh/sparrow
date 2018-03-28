package com.sparrow.kit;

import java.util.Collection;

public class CollectionKit {

	public static <T> boolean isEmpty(Collection<T> c) {
		return null == c || c.size() == 0;
	}
	
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
}
