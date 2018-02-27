package com.sparrow.kit;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sparrow.mvc.ModelAndView;
import com.sparrow.mvc.Signature;

public class ArgsKit {

	public static Object[] getParams(Signature signature) {
		
		Method method = signature.getRoute().getAction();
		HttpServletRequest  request = signature.getRequest();
		
		Parameter[] parameters = method.getParameters();
		String[] paramNames = AsmKit.getParamNamesByMethod(method);
		Object[] params = new Object[parameters.length];
		
		for(int i = 0, len = parameters.length; i < len; i++) {
			
			Parameter parameter = parameters[i];
			String paramName = paramNames[i];
			Class<?> clz = parameter.getClass();
			
			if(ReflectKit.isPrimitive(clz)) {
				params[i] = request.getParameter(paramName);
				//todo. 包装requeset
			} else {
				params[i] = getCustomType(parameter, signature);
			}
			
		}
		
		return params;
	}

	private static Object getCustomType(Parameter parameter, Signature signature) {
		Class<?> type = parameter.getType();
		if(type == HttpServletRequest.class) {
			return signature.getRequest();
		} else if(type == HttpServletResponse.class) {
			return signature.getResponse();
		} else if(type == ModelAndView.class) {
			return new ModelAndView();
		} else if(type == HttpSession.class) {
			return signature.getRequest().getSession();
		} else {
			return parseModel(type, signature.getRequest());
		}
		
	}

	private static Object parseModel(Class<?> type, HttpServletRequest request) {
		
//		Object obj = ReflectKit.instance(type);
		return null;
	}
	

}
