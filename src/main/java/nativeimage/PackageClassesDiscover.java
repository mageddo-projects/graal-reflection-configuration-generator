package nativeimage;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

public class PackageClassesDiscover {
	public Set<Class> discover(String packageName) {
		final Set<Class<?>> classes = new Reflections(
			new ConfigurationBuilder()
				.setScanners(
					new SubTypesScanner(false),
					new ResourcesScanner()
				)
				.addUrls(ClasspathHelper.forJavaClassPath())
				.filterInputsBy(
					new FilterBuilder()
						.include(FilterBuilder.prefix(packageName))
				)
		)
		.getSubTypesOf(Object.class)
		;
		return new LinkedHashSet<Class>(classes);
	}
}
