package com.mageddo.graal.reflection.configuration.generator

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ReflectionConfigurationGenerator {

	private static final Logger LOG  = LoggerFactory.getLogger(getClass())
	public static final ObjectMapper mapper = new ObjectMapper()
		.enable(SerializationFeature.INDENT_OUTPUT)

	static void generateJson(List<Class> classes, Writer writer) {
		def runtimeReflections = new LinkedHashMap<String, RuntimeReflectionVO>(classes.size())
		classes.each {
			if(runtimeReflections.containsKey(it.getName())){
				LOG.debug("status=already-generated, class={}", it.getName())
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
