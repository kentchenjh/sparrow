package com.test.controller;

import java.util.concurrent.atomic.AtomicInteger;

import com.sparrow.anno.Autowired;
import com.sparrow.anno.Controller;
import com.sparrow.anno.JSON;
import com.sparrow.anno.Path;
import com.sparrow.mvc.ModelAndView;
import com.test.service.TestService;

@Controller
public class TestController {

	static AtomicInteger count = new AtomicInteger(0);
	
	@Autowired
	TestService testSerivce;

	@Path("/home")
	@JSON
	public String homepage() {
		return "This is homepage";
	} 
	
	@Path("/count")
	@JSON
	public String count() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(count.incrementAndGet());
	}
	
	@Path("/hello")
	public ModelAndView hello(ModelAndView mav) {
		
		mav.setAttribute("name", "kent");
		mav.setView("hello");
		return mav;
	}
	
	@Path("/wtf/hi")
	public ModelAndView fuck(ModelAndView mav) {
		
		mav.setAttribute("name", "wtf hi");
		mav.setView("hello");
		return mav;
	}
	
	@Override
	public String toString() {
		return "TestController [testSerivce=" + testSerivce + "]";
	}
	
}
