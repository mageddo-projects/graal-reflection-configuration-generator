package com.mageddo.graal.reflection.configuration.generator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

class ReflectionConfigurationGenerator {

	public static final ObjectMapper mapper = new ObjectMapper()
		.enable(SerializationFeature.INDENT_OUTPUT)

	static void generateJson(List<Class> classes, Writer writer) {
		def runtimeReflections = new LinkedHashMap<String, RuntimeReflectionVO>(classes.size())
		classes.each {
			if(runtimeReflections.containsKey(it.getName())){
				return
			}
			def vo = RuntimeReflectionVO.valueOf(it)
			if(vo != null){
				runtimeReflections.put(it.getName(), vo)
			}
		}
		mapper.writeValue(writer, runtimeReflections.values())
	}
}
