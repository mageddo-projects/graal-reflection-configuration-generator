package nativeimage.core;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.Set;

public class PackageClassesDiscover {
	public Set<String> discover(Element element, String packageName) {
		final String className = element.toString();
		final String discoveredPackage = ClassUtils.getClassPackage(className);
		if(discoveredPackage.contains(packageName)){
			return Collections.singleton(className);
		}
		return Collections.emptySet();
	}

}
