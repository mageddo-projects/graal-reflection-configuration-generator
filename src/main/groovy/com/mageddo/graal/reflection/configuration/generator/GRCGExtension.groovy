package com.mageddo.graal.reflection.configuration.generator

import org.gradle.api.file.FileCollection

class GRCGExtension {

	String configurationFile
	FileCollection classpath

	FileCollection getClassPathOrDefault(FileCollection defaultValue){
		return Optional.ofNullable(classpath).orElse(defaultValue)
	}

	String getConfigurationFileOrDefault(String defaultValue){
		return Optional.ofNullable(configurationFile).orElse(defaultValue)
	}
}
