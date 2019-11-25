package nativeimage;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.lang.model.element.Element;
import java.util.Collections;
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

	public Set<String> discover(Element element, String packageName) {
		final String className = element.toString();
		final int endIndex = className.length() - element.getSimpleName().length() - 1;
		final String discoveredPackage = className.substring(0, endIndex);
		System.out.printf("name = %s, element=%s, package=%s, expected-package=%s %n", element.getSimpleName(), element, discoveredPackage, packageName);
		if(discoveredPackage.equals(packageName)){
			return Collections.singleton(className);
		}
		return Collections.emptySet();
	}
}
