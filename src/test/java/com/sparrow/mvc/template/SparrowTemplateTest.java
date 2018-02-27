package com.sparrow.mvc.template;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SparrowTemplateTest {

	static SparrowTemplate parser;
	
	@Before
	public void init() {
		
		StringBuilder content = new StringBuilder();
		content.append("<html>")
				.append("<body>")
				.append("``Hello ${name}!")
				.append("</body>")
				.append("</html>");
		
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("name", "kent");
		parser = SparrowTemplate.init(content.toString(), attrs);
	}
	
	@Test
	public void testParse() {
		
		String result = parser.parse();
		System.out.println(result);
		assertTrue(result.contains("kent"));
	}
	
}
