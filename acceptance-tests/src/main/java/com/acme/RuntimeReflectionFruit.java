package com.acme;

import nativeimage.Reflection;

@Reflection(allDeclaredConstructors = true, allDeclaredFields = true)
public class RuntimeReflectionFruit {

	private String name;
	private String color;

	public RuntimeReflectionFruit() {
	}

	public RuntimeReflectionFruit(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public RuntimeReflectionFruit setName(String name) {
		this.name = name;
		return this;
	}

	public String getColor() {
		return color;
	}

	public RuntimeReflectionFruit setColor(String color) {
		this.color = color;
		return this;
	}
}
