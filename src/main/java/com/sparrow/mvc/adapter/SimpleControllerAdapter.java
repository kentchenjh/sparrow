package com.sparrow.mvc.adapter;

import com.sparrow.kit.ReflectKit;
import com.sparrow.mvc.Signature;
import com.sparrow.mvc.annotation.Controller;
import com.sparrow.route.Route;

public class SimpleControllerAdapter implements RouteAdapter {

	@Override
	public Object handle(Signature signature) throws Exception {
		Route route = signature.getRoute();
		Object result = ReflectKit.invoke(route.getTarget(), route.getAction(), signature.getParams());
		return result;
	}

	@Override
	public boolean support(Route route) {
		return ReflectKit.hasAnnotation(Controller.class, route.getTargetType());
	}

}
