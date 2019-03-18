package com.mageddo.graal.reflection.configuration.generator.utils

final class ClassPathUtils {

	private ClassPathUtils(){}

	static ClassLoader classLoaderOf(File file){
		return new URLClassLoader([ file.toURI().toURL() ] as URL[])
	}
}
