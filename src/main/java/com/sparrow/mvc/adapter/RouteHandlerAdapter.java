package com.sparrow.mvc.adapter;

import com.sparrow.mvc.Signature;
import com.sparrow.route.Route;
import com.sparrow.route.RouteHandler;

public class RouteHandlerAdapter implements RouteAdapter{


	@Override
	public boolean support(Route route) {
		return RouteHandler.class.isAssignableFrom(route.getTargetType());
	}

	@Override
	public Object handle(Signature signature) {
		RouteHandler handler = (RouteHandler) signature.getRoute().getTarget();
		handler.handle(signature.getRequest(), signature.getResponse());
		return null;
	}

}
