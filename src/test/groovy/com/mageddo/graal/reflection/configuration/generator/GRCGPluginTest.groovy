package com.mageddo.graal.reflection.configuration.generator


import org.apache.commons.io.IOUtils
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import java.nio.file.Paths

import static org.apache.commons.io.FileUtils.copyDirectory
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class GRCGPluginTest {

	@Rule
	public TemporaryFolder testProjectDir = new TemporaryFolder()

	File buildFile

	@Test
	void shouldFindRuntimeReflectionClassesAndWriteTheJson() {

		// arrange

		def expectedConfigFile = """[ {
  "name" : "com.mageddo.FruitVO",
  "allDeclaredConstructors" : true,
  "allPublicConstructors" : true,
  "allPublicFields" : true,
  "allPublicMethods" : true,
  "allDeclaredMethods" : true,
  "allDeclaredFields" : true
}, {
  "name" : "com.mageddo.PersonVO",
  "allDeclaredConstructors" : true,
  "allPublicConstructors" : true,
  "allPublicFields" : true,
  "allPublicMethods" : true,
  "allDeclaredMethods" : true,
  "allDeclaredFields" : true
} ]"""

		def srcPath = testProjectDir.newFolder("src").toPath()
		copyDirectory(Paths.get(getClass().getResource("/classes").getFile()).toFile(), srcPath.toFile())
		buildFile = testProjectDir.newFile('build.gradle')
		buildFile << """
			plugins {
				id "java"
				id "com.mageddo.graal-reflection-configuration-generator"
			}
			
			sourceSets {
				main {
					java {
						srcDirs = ['${testProjectDir.root}/src']
					}
				}
			}
			
			repositories {
				mavenLocal()
			}
			
			dependencies {
				compileOnly ("com.mageddo:graal-reflection-configuration:2.0.0")
			}
			
		"""

		// act

		def result = GradleRunner.create()
			.withProjectDir(testProjectDir.root)
			.withArguments("build", "reflectConfigFiles")
			.withPluginClasspath()
			.build()

		// assert
		println(result.output)
		assertTrue(result.output.contains("reflection configuration written to"))
		result.task(":reflectConfigFiles").outcome == TaskOutcome.SUCCESS
		Paths.get("${testProjectDir.root}/build/reflect.json").withInputStream {
			assertEquals(expectedConfigFile, IOUtils.toString(it))
		}
	}
}
