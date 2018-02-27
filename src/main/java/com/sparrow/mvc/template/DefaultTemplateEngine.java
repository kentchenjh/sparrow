package com.sparrow.mvc.template;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sparrow.Const;
import com.sparrow.exception.TemplateException;
import com.sparrow.kit.IOKit;
import com.sparrow.kit.web.SessionKit;
import com.sparrow.mvc.ModelAndView;
import com.sparrow.mvc.WebContext;

public class DefaultTemplateEngine extends AbstractTemplateEngine{

	@Override
	public void render(ModelAndView mav) throws TemplateException {
		
		String view = mav.getView();
		
		String path = Const.CLASS_PATH + prefix + File.separator +view + suffix;
		Map<String, Object> attributes = new HashMap<>();
		
		HttpServletRequest request = WebContext.request();
		PrintWriter writer = null;
		attributes.putAll(request.getParameterMap());
		attributes.putAll(mav.getModel());
		//TODO CAN NOT GET SESSION
		attributes.putAll(SessionKit.getParamsFromSession(request.getSession()));
		
		try {
			String body = IOKit.readToString(path);
			String result = SparrowTemplate.init(body, attributes).parse();
			writer = WebContext.response().getWriter();
			writer.write(result);
		} catch (IOException e) {
			throw new TemplateException(e);
		} finally {
			IOKit.closeQuietly(writer);
		}
		
	}
	
	

}
