package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection

import java.lang.annotation.Annotation

class RuntimeReflectionVO {

	String name

	boolean allDeclaredConstructors

	boolean allPublicConstructors

	boolean allPublicFields

	boolean allPublicMethods

	boolean allDeclaredMethods

	boolean allDeclaredFields

	static RuntimeReflectionVO valueOf(Class clazz) {
		return Optional
		.ofNullable(valueOf(clazz.getName(), clazz.getAnnotations()))
		.orElseGet({
			return valueOf(clazz.getName(), clazz.getPackage().getAnnotations())
		})
	}

	static RuntimeReflectionVO valueOf(String className, Annotation[] annotations) {
		return Arrays.stream(annotations)
			.filter({ ann ->
				return ann.annotationType().getName() == RuntimeReflection.class.getName()
			})
			.map({ ann ->
				def vo = new RuntimeReflectionVO()

				vo.setName(className)

				vo.setAllDeclaredConstructors(ann.allDeclaredConstructors())
				vo.setAllPublicConstructors(ann.allPublicConstructors())

				vo.setAllDeclaredFields(ann.allDeclaredFields())
				vo.setAllPublicFields(ann.allPublicFields())

				vo.setAllDeclaredMethods(ann.allDeclaredMethods())
				vo.setAllPublicMethods(ann.allPublicMethods())
				return vo
			})
			.findFirst()
			.orElse(null)
	}
}
