package nativeimage;

import jdk.jfr.Experimental;
import lombok.experimental.UtilityClass;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@UtilityClass
@Experimental
public class TypeBuilder {

	public static Set<String> of(Element element, RuntimeReflection runtimeReflectionAnn) {
		if(!runtimeReflectionAnn.scanClassName().equals("")){
			return toSet(runtimeReflectionAnn.scanClassName());
		}
		final String scanClass = getScanClass(runtimeReflectionAnn);
		if(!scanClass.equals(Void.class.getName())){
			return toSet(scanClass);
		}
		if(!runtimeReflectionAnn.scanPackage().equals("")){
			final Set<Class> discoveredClasses = new PackageClassesDiscover().discover(runtimeReflectionAnn.scanPackage());
			final Set<String> discoveredClassNames = new LinkedHashSet<>(discoveredClasses.size());
			for (Class clazz : discoveredClasses) {
				discoveredClassNames.add(clazz.getName());
			}
			System.out.println(">>>>> discoveredClasses " + discoveredClasses);
			return discoveredClassNames;
		}
		return toSet(element.toString());
	}

	private static String getScanClass(RuntimeReflection runtimeReflectionAnn) {
		try {
			return runtimeReflectionAnn.scanClass().getName();
		} catch (MirroredTypeException e){
			return e.getTypeMirror().toString();
		}
	}

	private static Set<String> toSet(String type) {
		return Collections.singleton(type);
	}
}
