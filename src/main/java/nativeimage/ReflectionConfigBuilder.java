package nativeimage;

import jdk.jfr.Experimental;
import lombok.experimental.UtilityClass;
import nativeimage.core.ReflectionConfig;

import javax.lang.model.element.Element;
import java.util.LinkedHashSet;
import java.util.Set;

@Experimental
@UtilityClass
public class ReflectionConfigBuilder {

	public static Set<ReflectionConfig> of(Element element) {
		return of(element, element.getAnnotation(RuntimeReflection.class));
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
