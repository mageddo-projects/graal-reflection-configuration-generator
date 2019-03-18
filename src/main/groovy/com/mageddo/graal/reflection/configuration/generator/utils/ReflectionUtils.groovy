package com.mageddo.graal.reflection.configuration.generator.utils

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder

final class ReflectionUtils {
	private ReflectionUtils() {
	}

	static Reflections reflectionsOf(URLClassLoader cl){
		return reflectionsOf(cl, cl.URLs)
	}
	static Reflections reflectionsOf(ClassLoader cl, URL ... url){
		def reflections = new Reflections(
			new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(true), new TypeAnnotationsScanner())
				.addClassLoaders(cl)
				.setUrls(url)
		)
		return reflections
	}
}
