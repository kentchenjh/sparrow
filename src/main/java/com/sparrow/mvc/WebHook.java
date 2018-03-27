package com.sparrow.mvc;

public interface WebHook {

	boolean before(Signature signature);
	
	default boolean after(Signature signature) {
		return true;
	}
}
