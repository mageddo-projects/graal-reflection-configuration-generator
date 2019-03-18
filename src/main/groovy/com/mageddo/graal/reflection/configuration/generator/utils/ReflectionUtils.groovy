package com.mageddo.graal.reflection.configuration.generator.utils

import com.mageddo.graal.reflection.configuration.RuntimeReflection
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
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
				.setScanners(new SubTypesScanner(true), new TypeAnnotationsScanner(), new ResourcesScanner())
				.addClassLoaders(cl)
				.setUrls(url)
		)
		return reflections
	}

	static URL getRuntimeReflectionURL() {
		def clazzInsideJarKey = ".jar!"
		def clazz = RuntimeReflection.class
		def url = getResourceAsURL(clazz)
		def str = url.getFile()
		if(!str.contains(clazzInsideJarKey)){
			return url
		}
		return new URL(str.substring(0, str.indexOf(clazzInsideJarKey) + 4))
	}

	private static URL getResourceAsURL(Class clazz) {
		return clazz.getResource(String.format("%s.class", clazz.getSimpleName()))
	}
}
