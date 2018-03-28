package com.test.kit;

import org.junit.Test;

import com.sparrow.kit.RegexKit;
import static org.junit.Assert.*;

public class RegexKitTest {

	@Test
	public void testMatch() {
		
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.*"), "com.service"));
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.dao"), "com.dao"));
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.*.dao.insert*"), "com.sparrow.dao.insertUser"));
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.sparrow.*User"), "com.sparrow.dao.deleteUser"));
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.*.*.*"), "com.sparrow.dao.updateUser"));
		assertTrue(RegexKit.match(RegexKit.pathToRegex("com.*.*.u*User"), "com.sparrow.dao.updateUser"));
		
	}
}
