package com.sparrow.kit;

import java.util.Collection;

public class CollectionKit<T> {

	public static <T> boolean isEmpty(Collection<T> c) {
		
		return null == c || c.size() == 0;
	}
}
