package nativeimage.core;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.Set;

public class PackageClassesDiscover {
	public Set<String> discover(Element element, String packageName) {
		final String className = element.toString();
		final int endIndex = className.length() - element.getSimpleName().length() - 1;
		final String discoveredPackage = className.substring(0, endIndex);
		if(discoveredPackage.equals(packageName)){
			return Collections.singleton(className);
		}
		return Collections.emptySet();
	}
}
