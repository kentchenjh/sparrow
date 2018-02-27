package com.sparrow.mvc;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ModelAndView {

	private Map<String, Object> model = new HashMap<>();
	private String view;
	
	public void setAttribute(String key, Object value) {
		model.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return model.get(key);
	}

	public ModelAndView(Map<String, Object> model, String view) {
		this.model.putAll(model);
		this.view = view;
	}
	
	public ModelAndView(String view) {
		this.view = view;
	}
	
	public ModelAndView() {};
}
