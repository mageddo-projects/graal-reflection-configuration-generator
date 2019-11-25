package nativeimage.core;

import nativeimage.RuntimeReflection;

import javax.lang.model.element.Element;
import java.util.LinkedHashSet;
import java.util.Set;

//@Experimental
public final class ReflectionConfigBuilder {

	private ReflectionConfigBuilder() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static Set<ReflectionConfig> of(Element element, final RuntimeReflection annotation){
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

	private static ReflectionConfig.ReflectionConfigBuilder toBuilder(RuntimeReflection runtimeReflectionAnn) {
		return ReflectionConfig
			.builder()
			.allPublicConstructors(runtimeReflectionAnn.allPublicConstructors())
			.allDeclaredConstructors(runtimeReflectionAnn.allDeclaredConstructors())

			.allPublicFields(runtimeReflectionAnn.allPublicFields())
			.allDeclaredFields(runtimeReflectionAnn.allDeclaredFields())

			.allPublicMethods(runtimeReflectionAnn.allPublicMethods())
			.allDeclaredMethods(runtimeReflectionAnn.allDeclaredMethods())
		;
	}
}
