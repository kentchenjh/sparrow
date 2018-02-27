package com.sparrow.mvc.template;

import com.sparrow.exception.TemplateException;
import com.sparrow.mvc.ModelAndView;

public interface TemplateEngine {
	public void render(ModelAndView mav) throws TemplateException;
}
