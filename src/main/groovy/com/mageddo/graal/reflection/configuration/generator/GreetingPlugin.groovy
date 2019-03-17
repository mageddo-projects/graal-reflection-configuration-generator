package com.mageddo.graal.reflection.configuration.generator

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
	void apply(Project project) {
		def extension = project.getExtensions().create("greeting", GreetingPluginExtension)
		project.task("hello"){
			doLast {
				println "${extension.message} from ${extension.greeter}"
			}
		}
	}
}
