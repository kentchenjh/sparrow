package com.test.aop;

import com.sparrow.anno.Hook;
import com.sparrow.aop.WebHook;
import com.sparrow.mvc.Signature;

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
