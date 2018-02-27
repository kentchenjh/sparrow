package com.sparrow.aop;

import com.sparrow.mvc.Signature;

public interface WebHook {

	boolean before(Signature signature);
	
	default boolean after(Signature signature) {
		return true;
	}
}
