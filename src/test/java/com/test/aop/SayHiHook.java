package com.test.aop;

import com.sparrow.mvc.Signature;
import com.sparrow.mvc.WebHook;
import com.sparrow.mvc.annotation.Hook;

@Hook
public class SayHiHook implements WebHook{

	@Override
	public boolean before(Signature signature) {
		
		System.out.println("HI   -  - - - -- -- - before");
		return true;
	}

	@Override
	public boolean after(Signature signature) {
		
		System.out.println("HI   -  - - - -- -- - after");
		return true;
	}
	
}
