package nativeimage.core.utils;

import nativeimage.RuntimeReflection;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public final class ReflectionUtils {

	private ReflectionUtils() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static Reflections reflectionsOf(URLClassLoader cl){
		return reflectionsOf(cl, cl.getURLs());
	}

	public static Reflections reflectionsOf(ClassLoader cl, URL... url){
		return new Reflections(
			new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(true), new TypeAnnotationsScanner(), new ResourcesScanner())
				.addClassLoaders(cl)
				.setUrls(url)
		);
	}

	public static URL getRuntimeReflectionURL() {
		String clazzInsideJarKey = ".jar!";
		Class clazz = RuntimeReflection.class;
		URL url = getResourceAsURL(clazz);
		String str = url.getFile();
		if(!str.contains(clazzInsideJarKey)){
			return url;
		}
		try {
			return new URL(str.substring(0, str.indexOf(clazzInsideJarKey) + clazzInsideJarKey.length()));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static URL getResourceAsURL(Class clazz) {
		return clazz.getResource(String.format("%s.class", clazz.getSimpleName()));
	}
}
