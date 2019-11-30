package com.acme;

import com.acme.vo.Fruit;
import com.fasterxml.jackson.databind.ObjectMapper;
import nativeimage.Reflection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Reflection(declaredFields = true, declaredMethods = true, declaredConstructors = true, scanPackage = "com.acme.vo")
public class Main {

	private static final ObjectMapper om = new ObjectMapper();
	private static final String fruitJson = "{\"name\":\"Grape\"}";

	public static void main(String[] args) throws Exception {

		final Fruit fruit = om.readValue(fruitJson, Fruit.class);
		System.out.println("> outer class");
		System.out.printf("\tparsed = %s%n", fruit);
		System.out.printf("\tserialized = %s%n", om.writeValueAsString(fruit));

		System.out.println("> inner class");
		System.out.printf("\tserialized = %s%n", om.writeValueAsString(new Fruit.SubClass("Stuff")));

		System.out.printf("default constructor by reflection = %s%n", makeInstance(Class.forName("com.acme.vo.Fruit$SubClass")));

	}

	private static Object makeInstance(Class<?> clazz) throws Exception {
		return clazz.getConstructor().newInstance();
	}
}
