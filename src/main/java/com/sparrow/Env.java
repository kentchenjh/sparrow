package com.sparrow;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import com.sparrow.kit.IOKit;

import lombok.NonNull;

public class Env {

	private Properties prop = new Properties();
	
	private Env() {};
	
	public static Env empty() {
		return new Env();
	}
	
	public void of(Collection<String> locations) {
		for(String loc : locations) {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(loc);
			of(is);
		}
	}
	
	public void of(InputStream is) {
		try {
			this.prop.load(new InputStreamReader(is, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOKit.closeQuietly(is);
		}
	}
	
	public void set(@NonNull String key, @NonNull Object val) {
		prop.put(key, val);
	}
	
	public String get(@NonNull String key, String defultVal) {
		return get(key).orElse(defultVal);
	}
	
	public Optional<String> get(@NonNull String key) {
		return Optional.ofNullable(prop.getProperty(key));
	}
	
	public int getInt(@NonNull String key, @NonNull String defaultVal) {
		return Integer.parseInt((get(key, defaultVal)));
	}
}

