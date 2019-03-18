package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.nio.file.Paths
import java.util.stream.Collectors

import static com.mageddo.graal.reflection.configuration.generator.ReflectionConfigurationGenerator.generateJson
import static com.mageddo.graal.reflection.configuration.generator.utils.ClassPathUtils.classLoaderOf
import static com.mageddo.graal.reflection.configuration.generator.utils.ReflectionUtils.getRuntimeReflectionURL
import static com.mageddo.graal.reflection.configuration.generator.utils.ReflectionUtils.reflectionsOf

class GRCGPlugin implements Plugin<Project> {
	void apply(Project project) {
		def extension = project.getExtensions().create("reflectConfigFiles", GRCGExtension)
		project.task("reflectConfigFiles") {
			doLast {

				try {
					final List<Class> runtimeReflectionClasses = new ArrayList<>()
					def classPath =
					extension
//						.getClassPathOrDefault(project.sourceSets.main.output.classesDirs)
						.getClassPathOrDefault(project.sourceSets.main.runtimeClasspath)
						.files
						.stream()
						.map({it.toURI().toURL()})
						.collect(Collectors.toSet())

					classPath.add(getRuntimeReflectionURL())

					println("> loading classpath ${classPath}")
					def classLoader = classLoaderOf(classPath)
					def reflections = reflectionsOf(classLoader, classPath as URL[])
					runtimeReflectionClasses.addAll(reflections.getTypesAnnotatedWith(RuntimeReflection.class))

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
