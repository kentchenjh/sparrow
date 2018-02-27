package com.sparrow.kit;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IOKit {

	public static void closeQuietly(Closeable closeable) {
        try {
            if (null == closeable) {
                return;
            }
            closeable.close();
        } catch (Exception e) {
            log.error("Close closeable error", e);
        }
    }
	
	public static String readToString(InputStream is) throws Exception {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
	}
	
	public static String readToString(Path path) throws IOException {
		BufferedReader reader = Files.newBufferedReader(path);
		return reader.lines().collect(Collectors.joining("\n"));
	}
	
	public static String readToString(String file) throws IOException {
        return readToString(Paths.get(file));
    }
}
