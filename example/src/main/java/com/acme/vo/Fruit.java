package com.acme.vo;

import nativeimage.RuntimeReflection;

@RuntimeReflection
public class Fruit {

	private String name;

	public Fruit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Fruit setName(String name) {
		this.name = name;
		return this;
	}
}
