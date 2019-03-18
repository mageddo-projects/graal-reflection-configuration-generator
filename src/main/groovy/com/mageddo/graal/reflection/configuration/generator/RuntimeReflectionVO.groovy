package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection

class RuntimeReflectionVO {

	String name

	boolean allDeclaredConstructors

	boolean allPublicConstructors

	boolean allPublicFields

	boolean allPublicMethods

	boolean allDeclaredMethods

	boolean allDeclaredFields

	static RuntimeReflectionVO valueOf(Class clazz) {
		println "> Annotations ${clazz.getAnnotations()}"
		return Arrays.stream(clazz.getAnnotations())
		.filter( { ann ->
			return ann.annotationType().getName() == RuntimeReflection.class.getName()
		})
		.map({ann ->
			def vo = new RuntimeReflectionVO()

			vo.setName(clazz.getName())

			vo.setAllDeclaredConstructors(ann.allDeclaredConstructors())
			vo.setAllPublicConstructors(ann.allPublicConstructors())

			vo.setAllDeclaredFields(ann.allDeclaredFields())
			vo.setAllPublicFields(ann.allPublicFields())

			vo.setAllDeclaredMethods(ann.allDeclaredMethods())
			vo.setAllPublicMethods(ann.allPublicMethods())
			return vo
		})
		.findFirst()
		.orElseThrow({
			throw new RuntimeException("Cant' find annotation on clazz ${clazz}")
		})

	}
}
