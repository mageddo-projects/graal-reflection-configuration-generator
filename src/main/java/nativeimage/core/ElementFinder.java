package nativeimage.core;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.List;

public class ElementFinder {

	private ElementFinder() {
	}

	public static List<Element> find(Element e, ElementKind kind){
		final List<Element> elements = new ArrayList<>();
		find(e, kind, elements);
		return elements;
	}

	private static void find(Element e, ElementKind kind, List<Element> elements) {
		for (final Element enclosedElement : e.getEnclosedElements()) {
			if(enclosedElement.getKind().equals(kind)){
				elements.add(enclosedElement);
			}
			find(enclosedElement, kind, elements);
		}
	}
}
