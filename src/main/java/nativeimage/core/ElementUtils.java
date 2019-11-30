package nativeimage.core;

import org.apache.commons.lang3.Validate;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ElementUtils {
	private ElementUtils() {
	}

	public static String toClassName(Element element) {
		Validate.isTrue(
			element.getKind() == ElementKind.CLASS,
			"element type must be class but is %s", element.getKind()
		);
		if(element.getEnclosingElement() == null){
			return element.toString();
		}
		if(element.getEnclosingElement().getKind() == ElementKind.CLASS){
			return String.format("%s\u0024%s", element.getEnclosingElement().toString(), element.getSimpleName());
		}
		return element.toString();
	}
}
