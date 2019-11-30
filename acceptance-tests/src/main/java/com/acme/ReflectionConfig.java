package com.acme;

import nativeimage.Reflection;
import nativeimage.Reflections;

@Reflections({
	@Reflection(
		scanClass = Fruit.class,
		allDeclaredFields = true, allPublicFields = true,
		allDeclaredMethods = true, allPublicMethods = true,
		allDeclaredConstructors = true, allPublicConstructors = true
	),
	@Reflection(
		scanClassName = "com.acme.Car",
		allDeclaredConstructors = true, allDeclaredFields = true, allDeclaredMethods = true
	),
	@Reflection(
		scanPackage = "com.acme.subpackage",
		allDeclaredFields = true
	)
})
public class ReflectionConfig {
}
