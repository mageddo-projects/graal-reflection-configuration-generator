package nativeimage.core.utils;

import com.mageddo.graal.reflection.configuration.RuntimeReflection;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.net.URL;
import java.net.URLClassLoader;

@UtilityClass
public class ReflectionUtils {

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

	@SneakyThrows
	public static URL getRuntimeReflectionURL() {
		val clazzInsideJarKey = ".jar!";
		val clazz = RuntimeReflection.class;
		val url = getResourceAsURL(clazz);
		val str = url.getFile();
		if(!str.contains(clazzInsideJarKey)){
			return url;
		}
		return new URL(str.substring(0, str.indexOf(clazzInsideJarKey) + clazzInsideJarKey.length()));
	}

	private static URL getResourceAsURL(Class clazz) {
		return clazz.getResource(String.format("%s.class", clazz.getSimpleName()));
	}
}
