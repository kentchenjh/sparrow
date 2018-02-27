package com.test.aop;

import com.sparrow.anno.Hook;

import com.sparrow.aop.WebHook;
import com.sparrow.mvc.Signature;

@Hook("/wtf/h*")
public class ModifyHook implements WebHook{

	@Override
	public boolean before(Signature signature) {
		
		System.out.println("modfiy hook before");
		
		return true;
	}
}
