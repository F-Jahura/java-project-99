plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.sonarqube") version "6.3.1.5724"
	checkstyle
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation ("com.h2database:h2")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
	useJUnitPlatform()
}


checkstyle {
	toolVersion = "9.0"
	configDirectory.set(file("config/checkstyle"))
}

tasks.withType<Checkstyle>().configureEach {
	classpath = files("${project.rootDir}/src/test/java")
}

val myCheckstyleTest by tasks.registering(Checkstyle::class) {
	source("src/test/java")
	classpath = files()
	configFile = file("${project.rootDir}/config/checkstyle/checkstyle.xml")
	include("**/*.java")
	exclude("**/generated/**")
}

tasks.named("check") {
	dependsOn(myCheckstyleTest)
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}


tasks.withType<Test> {
	finalizedBy(tasks.jacocoTestReport)
}


sonar {
	properties {
		property("sonar.projectKey", "F-Jahura_java-project-99")
		property("sonar.organization", "f-jahura")
		property("sonar.host.url", "https://sonarcloud.io")
		property ("sonar.login", "${System.getenv("SONAR_TOKEN")}")
		property("sonar.java.binaries", "${buildDir}/classes/java/main")
		property ("sonar.java.coveragePlugin", "jacoco")
		property ("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco/test/jacocoTestReport.xml")

	}
}
