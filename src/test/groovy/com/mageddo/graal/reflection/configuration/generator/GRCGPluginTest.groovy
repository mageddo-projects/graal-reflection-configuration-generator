package com.mageddo.graal.reflection.configuration.generator

import org.apache.commons.io.IOUtils
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import java.nio.file.Paths

import static java.nio.file.Files.newOutputStream
import static org.apache.commons.io.IOUtils.copy
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
  "name" : "FruitVO",
  "allDeclaredConstructors" : false,
  "allPublicConstructors" : false,
  "allPublicFields" : false,
  "allPublicMethods" : false,
  "allDeclaredMethods" : false,
  "allDeclaredFields" : false
} ]"""
		def clazzName = "FruitVO.java"
		def srcPath = testProjectDir.newFolder("src").toPath()
		copy getClass().getResourceAsStream("/${clazzName}"), newOutputStream(srcPath.resolve(clazzName))

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
				compileOnly ("com.mageddo:graal-reflection-configuration:1.0.0")
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
