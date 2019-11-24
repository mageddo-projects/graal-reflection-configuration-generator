package nativeimage;

import lombok.experimental.UtilityClass;

import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.Set;

@UtilityClass
public class ClassBuilder {

	public static Set<Class> of(Element element, RuntimeReflection runtimeReflectionAnn) {
		try {
			if(!runtimeReflectionAnn.scanClassName().equals("")){
				return toSet(Class.forName(runtimeReflectionAnn.scanClassName()));
			}
			if(runtimeReflectionAnn.scanClass() != Void.class){
				return toSet(runtimeReflectionAnn.scanClass());
			}
			if(!runtimeReflectionAnn.scanPackage().equals("")){
				return new PackageClassesDiscover().discover(runtimeReflectionAnn.scanPackage());
			}
			return toSet(Class.forName(element.toString()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static Set<Class> toSet(Class clazz) throws ClassNotFoundException {
		return Collections.singleton(clazz);
	}
}
