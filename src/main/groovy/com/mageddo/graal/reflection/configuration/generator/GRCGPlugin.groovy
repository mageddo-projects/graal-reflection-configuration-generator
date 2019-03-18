package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.file.Paths

import static com.mageddo.graal.reflection.configuration.generator.ReflectionConfigurationGenerator.generateJson
import static com.mageddo.graal.reflection.configuration.generator.utils.ClassPathUtils.classLoaderOf
import static com.mageddo.graal.reflection.configuration.generator.utils.ReflectionUtils.reflectionsOf

class GRCGPlugin implements Plugin<Project> {
	void apply(Project project) {
		def extension = project.getExtensions().create("reflectConfigFiles", GRCGExtension)
		project.task("reflectConfigFiles") {
			doLast {

				try {
					final List<Class> runtimeReflectionClasses = new ArrayList<>()
					extension.getClassPathOrDefault(project.sourceSets.main.output.classesDirs).each {

						if (it.isFile()) {
							return
						}
						println("> loading ${it}")
						def reflections = reflectionsOf(classLoaderOf(it))
						runtimeReflectionClasses.addAll(reflections.getTypesAnnotatedWith(RuntimeReflection.class))

					}

					def outFile = Paths.get(extension.getConfigurationFileOrDefault("${project.buildDir}/reflect.json"))
					outFile.withWriter {
						generateJson(runtimeReflectionClasses, it)
					}
					println("> reflection configuration written to ${outFile}")

				} catch (Throwable e) {
					println "> can't load project classes ${e.getClass()}"
					e.printStackTrace()
					throw e
				}
			}
		}
	}
}
