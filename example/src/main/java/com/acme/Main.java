package com.acme;

import com.acme.vo.Fruit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	public static void main(String[] args) throws JsonProcessingException {
		final Fruit grape = new Fruit("Grape");
		System.out.println(new ObjectMapper().writeValueAsString(grape));;
	}
}
