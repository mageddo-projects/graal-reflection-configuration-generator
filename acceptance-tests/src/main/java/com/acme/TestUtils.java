package com.acme;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class TestUtils {
	public static String getResourceAsString(String path) {
		try {
			return IOUtils.toString(TestUtils.class.getResourceAsStream(path), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
