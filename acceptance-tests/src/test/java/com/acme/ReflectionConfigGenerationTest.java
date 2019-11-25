package com.acme;

import org.junit.jupiter.api.Test;

import static com.acme.TestUtils.getResourceAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionConfigGenerationTest {

	@Test
	void mustConfigureReflectJson(){
		// arrange

		// act
		final String reflectJson = getResourceAsString("/META-INF/native-image/com.acme/reflect.json");

		// assert
		assertEquals(getResourceAsString("/reflection-config-generation-test/001.json"), reflectJson);
	}

	@Test
	void mustConfigureNativeImagePropertiesFile(){
		// arrange

		// act
		final String propsContent = getResourceAsString("/META-INF/native-image/com.acme/native-image.properties");

		// assert
		assertEquals(getResourceAsString("/reflection-config-generation-test/002.properties"), propsContent);
	}
}
