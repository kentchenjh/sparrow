package com.sparrow.mvc.handler;

import com.sparrow.mvc.Signature;

public interface ExceptionHandler {
	public void handleException(Exception e, Signature signature);
}
