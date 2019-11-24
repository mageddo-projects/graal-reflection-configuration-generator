package nativeimage.core.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

@UtilityClass
public class ClassPathUtils {

	@SneakyThrows
	public static URLClassLoader classLoaderOf(Path file){
		return new URLClassLoader(new URL[]{file.toFile().toURI().toURL()});
	}

	public static URLClassLoader classLoaderOf(Iterable<URL> urls){
//		return new URLClassLoader(urls);
		throw new UnsupportedOperationException();
	}
}
