package com.acme;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionConfigGenerationTest {

	@Test
	void mustConfigureReflectJson(){
		// arrange

		// act
		final String reflectJson = TestUtils.getResourceAsString("/META-INF/reflect.json");

		// assert
		assertEquals(TestUtils.getResourceAsString("/reflection-config-generation-test/001.json"), reflectJson);

		// assert
	}
}
