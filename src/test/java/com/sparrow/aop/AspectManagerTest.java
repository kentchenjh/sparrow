package com.sparrow.aop;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.sparrow.aop.annotation.Aop;

public class AspectManagerTest {

	static AspectManager aspectManager = new AspectManager();
	
	@Before
	public void init() {
		aspectManager.addAspect(Aspect1.class, new Aspect1());
		aspectManager.addAspect(Aspect2.class, new Aspect2());
		aspectManager.addAspect(Aspect3.class, new Aspect3());
	}
	
	
	@Test
	public void getAspect() {
		assertTrue(aspectManager.getAspect("com.sparrow.service").size() == 1);
		assertTrue(aspectManager.getAspect("com.sparrow.dao.insertUser").size() == 1);
		assertTrue(aspectManager.getAspect("com.sparrow.dao.delete").size() == 1);
		assertTrue(aspectManager.getAspect("com.sparrow.dao.deleteUser").size() == 0);
	}
	
	@Aop("com.sparrow.*vice")
	static class Aspect1 implements Aspect {
		
	}
	
	@Aop("com.sparrow.dao.insert*")
	static class Aspect2 implements Aspect {
		
	}
	
	@Aop("com.sparrow.dao.delete")
	static class Aspect3 implements Aspect {
		
	}
}
