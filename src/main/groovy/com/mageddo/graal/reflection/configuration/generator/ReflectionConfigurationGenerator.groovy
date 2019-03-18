package com.mageddo.graal.reflection.configuration.generator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

class ReflectionConfigurationGenerator {

	public static final ObjectMapper mapper = new ObjectMapper()
		.enable(SerializationFeature.INDENT_OUTPUT)

	static void generateJson(List<Class> classes, Writer writer) {
		def runtimeReflections = new ArrayList<RuntimeReflectionVO>(classes.size())
		classes.each {
			runtimeReflections.add(RuntimeReflectionVO.valueOf(it))
		}
		mapper.writeValue(writer, runtimeReflections)
	}
}
