package com.mageddo.graal.reflection.configuration.generator.utils

final class ClassPathUtils {

	private ClassPathUtils(){}

	static URLClassLoader classLoaderOf(File file){
		return new URLClassLoader([ file.toURI().toURL() ] as URL[])
	}

	static URLClassLoader classLoaderOf(Iterable<URL> urls){
		return new URLClassLoader(urls as URL[])
	}
}
