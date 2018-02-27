package com.sparrow.kit;

import lombok.NonNull;

public class FileKit {

	
	public static String getPath(String packageName) {
		return packageName.replace(".", "/");
	}
	
	public static String getSuffix(@NonNull String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
