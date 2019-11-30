package nativeimage.core;

import nativeimage.Reflection;

import javax.lang.model.element.Element;
import java.util.LinkedHashSet;
import java.util.Set;

//@Experimental
public final class ReflectionConfigBuilder {

	private ReflectionConfigBuilder() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static Set<ReflectionConfig> of(Element element, final Reflection annotation){
		final Set<ReflectionConfig> reflectionConfigs = new LinkedHashSet<>();
		for (String type : TypeBuilder.of(element, annotation)) {
			reflectionConfigs.add(
				toBuilder(annotation)
				.type(type)
				.build()
			);
		}
		return reflectionConfigs;
	}

	private static ReflectionConfig.ReflectionConfigBuilder toBuilder(Reflection reflectionAnn) {
		return ReflectionConfig
			.builder()
			.allPublicConstructors(reflectionAnn.publicConstructors())
			.allDeclaredConstructors(reflectionAnn.declaredConstructors())

			.allPublicFields(reflectionAnn.publicFields())
			.allDeclaredFields(reflectionAnn.declaredFields())

			.allPublicMethods(reflectionAnn.publicMethods())
			.allDeclaredMethods(reflectionAnn.declaredMethods())
		;
	}
}
