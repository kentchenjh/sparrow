package com.sparrow.kit;

import java.util.regex.Pattern;

public class RegexKit {

	/**
	 * translate path to regex style
	 * @param path
	 * @return
	 */
	public static String pathToRegex(String path) {
		return path.replace(".","\\.").replace("/", "\\/").replace("*", ".*");
	}
	
	public static boolean match(String regex, String str) {
		Pattern p = Pattern.compile(regex);
		return p.matcher(str).matches();
	}
	
}
