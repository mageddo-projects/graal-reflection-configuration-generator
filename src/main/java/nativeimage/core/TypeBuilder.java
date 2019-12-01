package nativeimage.core;

import nativeimage.Reflection;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import java.util.Collections;
import java.util.Set;

public final class TypeBuilder {

	private TypeBuilder() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static Set<String> of(Element element, Reflection reflectionAnn) {
		if(!reflectionAnn.scanClassName().equals("")){
			return toSet(reflectionAnn.scanClassName());
		}
		final String scanClass = getScanClass(reflectionAnn);
		if(!scanClass.equals(Void.class.getName())){
			return toSet(scanClass);
		}
		if(!reflectionAnn.scanPackage().equals("")){
			return new PackageClassesDiscover().discover(element, reflectionAnn.scanPackage());
		}
		return toSet(ElementUtils.toClassName(element));
	}

	private static String getScanClass(Reflection reflectionAnn) {
		try {
			return reflectionAnn.scanClass().getName();
		} catch (MirroredTypeException e){
			return e.getTypeMirror().toString();
		}
	}

	private static Set<String> toSet(String type) {
		return Collections.singleton(type);
	}
}
