package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection

import static java.util.Objects.requireNonNull

class RuntimeReflectionVO {

	String name

	boolean allDeclaredConstructors

	boolean allPublicConstructors

	boolean allPublicFields

	boolean allPublicMethods

	boolean allDeclaredMethods

	boolean allDeclaredFields

	static RuntimeReflectionVO valueOf(Class clazz) {

		println("${clazz}" + Arrays.toString(clazz.get))

		def ann = requireNonNull(clazz.getAnnotation(RuntimeReflection.class), "There is no annotation for class ${clazz}")
		def vo = new RuntimeReflectionVO()

		vo.setName(clazz.getName())

		vo.setAllDeclaredConstructors(ann.allDeclaredConstructors())
		vo.setAllPublicConstructors(ann.allPublicConstructors())

		vo.setAllDeclaredFields(ann.allDeclaredFields())
		vo.setAllPublicFields(ann.allPublicFields())

		vo.setAllDeclaredMethods(ann.allDeclaredMethods())
		vo.setAllPublicMethods(ann.allPublicMethods())
		return vo
	}
}
