package com.acme;

import nativeimage.RuntimeReflection;
import nativeimage.RepeatableRuntimeReflection;

@RepeatableRuntimeReflection({
	@RuntimeReflection(
		scanClass = Fruit.class,
		allDeclaredFields = true, allPublicFields = true,
		allDeclaredMethods = true, allPublicMethods = true,
		allDeclaredConstructors = true, allPublicConstructors = true
	),
	@RuntimeReflection(
		scanClassName = "com.acme.Car",
		allDeclaredConstructors = true, allDeclaredFields = true, allDeclaredMethods = true
	),
	@RuntimeReflection(
		scanPackage = "com.acme.subpackage",
		allDeclaredFields = true
	)
})
public class ReflectionConfig {
}
