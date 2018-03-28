package com.test.service;

import com.sparrow.mvc.annotation.Service;

@Service
public class NonInterfaceService {

	public void insertUser() {
		System.out.println("non interface serivce insert user");
	}
}
