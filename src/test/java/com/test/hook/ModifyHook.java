package com.test.hook;

import com.sparrow.mvc.Signature;
import com.sparrow.mvc.WebHook;
import com.sparrow.mvc.annotation.Hook;

@Hook("/wtf/h*")
public class ModifyHook implements WebHook{

	@Override
	public boolean before(Signature signature) {
		
		System.out.println("modfiy hook before");
		
		return true;
	}
}
