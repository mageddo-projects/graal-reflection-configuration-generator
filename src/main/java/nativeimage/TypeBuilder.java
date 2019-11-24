package nativeimage;

import com.sun.tools.javac.code.Type;
import lombok.experimental.UtilityClass;

import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.Set;

@UtilityClass
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
			final Set<Class> discover = new PackageClassesDiscover().discover(runtimeReflectionAnn.scanPackage());
			System.out.println(">>>>> discover " + discover);
			return toSet("");
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
