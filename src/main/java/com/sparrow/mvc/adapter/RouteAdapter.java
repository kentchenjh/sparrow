package com.sparrow.mvc.adapter;

import com.sparrow.mvc.Signature;
import com.sparrow.route.Route;

public interface RouteAdapter {

	Object handle(Signature signature);
	
	boolean support(Route route);
}
