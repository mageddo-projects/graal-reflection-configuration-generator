package com.mageddo.graal.reflection.configuration.generator


import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static java.nio.file.Files.newOutputStream
import static org.apache.commons.io.IOUtils.copy

class GRCGPluginTest {

	@Rule
	public TemporaryFolder testProjectDir = new TemporaryFolder()

	File buildFile

	@Test
	void setup() {

		// arrange

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
				compile ("com.mageddo:graal-reflection-configuration:1.0.0")
			}
			
		"""

		// act

		def result = GradleRunner.create()
			.withProjectDir(testProjectDir.root)
			.withArguments("build", "reflectConfigFiles")
			.withPluginClasspath()
			.build()

		println result.output
//		result.task(":verifyUrl").outcome == SUCCESS

//	@Test
//	void greeterPluginAddsGreetingTaskToProject() {
//		final Project project = ProjectBuilder.builder().build();
//		project.getPluginManager().apply("com.mageddo.graal-reflection-configuration-generator");
//
//		final Task task = project.getTasks().findByPath("reflectConfigFiles");
//		project.exe
//	}
	}
}
