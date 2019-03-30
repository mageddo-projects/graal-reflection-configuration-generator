package com.mageddo.graal.reflection.configuration.generator

import com.mageddo.graal.reflection.configuration.RuntimeReflection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

import java.nio.file.Paths
import java.util.stream.Collectors

import static com.mageddo.graal.reflection.configuration.generator.ReflectionConfigurationGenerator.generateJson
import static com.mageddo.graal.reflection.configuration.generator.utils.ClassPathUtils.classLoaderOf
import static com.mageddo.graal.reflection.configuration.generator.utils.ReflectionUtils.getRuntimeReflectionURL
import static com.mageddo.graal.reflection.configuration.generator.utils.ReflectionUtils.reflectionsOf

class GRCGPlugin implements Plugin<Project>  {
	void apply(Project project) {
		Reflections.log = null
		def extension = project.getExtensions().create("reflectConfigFiles", GRCGExtension)
		project.task("reflectConfigFiles") {
			doLast {

				try {
					final List<Class> runtimeReflectionClasses = new ArrayList<>()
					def classPath =
					extension
						.getClassPathOrDefault(project.sourceSets.main.runtimeClasspath)
						.files
						.stream()
						.filter({it.exists()})
						.map({it.toURI().toURL()})
						.collect(Collectors.toSet())

					classPath.add(getRuntimeReflectionURL())

					println("> loading classpath ${classPath}")

					def reflections = reflectionsOf(classLoaderOf(classPath), classPath as URL[])
					def runtimeClasses = reflections.getTypesAnnotatedWith(RuntimeReflection.class)

					def classes = filterClasses(runtimeClasses)
					println("> ${classes.size()} found ")
					runtimeReflectionClasses.addAll(classes)

					def packages = filterPackages(runtimeClasses)
					println("> scanning packages ${packages}")
					packages.each { Class<?> clazz ->
						def packageName = clazz.getPackage().getName()
						println("> scanning package ${packageName}")
						def packageClasses = new Reflections(
							new ConfigurationBuilder()
								.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new ResourcesScanner())
								.addClassLoaders(classLoaderOf(classPath))
								.setUrls(classPath as URL[])
								.filterInputsBy(new FilterBuilder().includePackage(packageName))
						)
						.getSubTypesOf(Object.class)

						def classes2 = filterClasses(packageClasses)
						println("> ${classes2.size()} found ${classes2}")
						runtimeReflectionClasses.addAll(classes2)

					}


					def outFile = Paths.get(extension.getConfigurationFileOrDefault("${project.buildDir}/reflect.json"))
					outFile.withWriter {
						generateJson(runtimeReflectionClasses, it)
					}
					println("> reflection configuration written to ${outFile}")

				} catch (Throwable e) {
					println("> can't load project classes ${e.getClass()}")
					e.printStackTrace()
					throw e
				}
			}
		}
	}

	static Set<Class> filterClasses(Set<Class> classes) {
		return classes
		.stream()
		.filter({
			return !it.name.contains("package-info")
		})
		.collect(Collectors.toSet())
	}

	static Set<Class<?>> filterPackages(Set<Class> classes) {
		return classes
		.stream()
		.filter({
			return it.name.contains("package-info")
		})
		.collect(Collectors.toSet())
	}
}
