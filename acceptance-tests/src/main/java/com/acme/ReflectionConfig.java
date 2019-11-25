package com.acme;

import nativeimage.RuntimeReflection;
import nativeimage.RepeatbleRuntimeReflection;

@RepeatbleRuntimeReflection({
	@RuntimeReflection(
		scanClass = Fruit.class,
		allDeclaredFields = true, allPublicFields = true,
		allDeclaredMethods = true, allPublicMethods = true,
		allDeclaredConstructors = true, allPublicConstructors = true
	),
	@RuntimeReflection(
		scanClassName = "com.acme.Car",
		allDeclaredConstructors = true, allDeclaredFields = true, allDeclaredMethods = true
	)
})
public class ReflectionConfig {
}
