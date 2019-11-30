package com.acme;

import com.acme.vo.Fruit;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {

	private static final ObjectMapper om = new ObjectMapper();
	private static final String fruitJson = "{\"name\":\"Grape\"}";

	public static void main(String[] args) throws IOException {
		final Fruit fruit = om.readValue(fruitJson, Fruit.class);
		System.out.println("> outer class");
		System.out.printf("\tparsed = %s%n", fruit);
		System.out.printf("\tserialized = %s%n", om.writeValueAsString(fruit));
		System.out.println("> inner class");
		System.out.printf("\tserialized = %s%n", om.writeValueAsString(new Fruit.SubClass("Stuff")));
	}
}
