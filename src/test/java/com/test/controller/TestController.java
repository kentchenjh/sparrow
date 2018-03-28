package com.test.controller;

import java.util.concurrent.atomic.AtomicInteger;

import com.sparrow.ioc.annotation.Autowired;
import com.sparrow.mvc.ModelAndView;
import com.sparrow.mvc.annotation.Controller;
import com.sparrow.mvc.annotation.JSON;
import com.sparrow.mvc.annotation.Path;
import com.test.service.NonInterfaceService;
import com.test.service.TestService;

@Controller
public class TestController {

	static AtomicInteger count = new AtomicInteger(0);
	
//	@Autowired
	TestService testSerivce;

	@Autowired
	NonInterfaceService nonInterfaceService;
	
	@Path("/home")
	@JSON
	public String homepage() {
		return "This is homepage";
	}
	
	@Path("/insertUser")
	@JSON
	public String insertUser() {
		testSerivce.insertUser();
		return "insertUser";
	}
	
	@Path("/insertBook")
	@JSON
	public String insertBook() {
		testSerivce.insertBook();
		return "insertBook";
	}
	
	@Path("/deleteUser")
	@JSON
	public String deleteUser() {
		testSerivce.deleteUser();
		return "deleteUser";
	}
	
	
	@Path("/exception")
	public String exception() throws Exception {
		throw new Exception("This is exception page");
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
