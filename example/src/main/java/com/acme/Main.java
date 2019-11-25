package com.acme;

import com.acme.vo.Fruit;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {

	private static final ObjectMapper om = new ObjectMapper();
	private static final String fruitJson = "{\"name\":\"Grape\"}";

	public static void main(String[] args) throws IOException {
		final Fruit fruit = om.readValue(fruitJson, Fruit.class);
		System.out.printf("parsed = %s%n", fruit);
		System.out.printf("serialized = %s%n", om.writeValueAsString(fruit));
	}
}
