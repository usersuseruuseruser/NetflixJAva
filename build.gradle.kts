import java.util.*

plugins {
	java
	id("java")
	id("org.springframework.boot") version "2.7.17"
	id("io.spring.dependency-management") version "1.1.4"
	id("application")
	id("org.liquibase.gradle") version "2.2.0"
	id("jacoco")
}

apply(plugin = "io.spring.dependency-management")

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.liquibase:liquibase-core")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.security:spring-security-taglibs:${properties["springSecurityVersion"]}")
	annotationProcessor("org.hibernate:hibernate-jpamodelgen:${properties["hibernateVersion"]}")
	implementation("org.postgresql:postgresql:42.7.2")
	liquibaseRuntime("org.liquibase:liquibase-core:4.20.0")
	liquibaseRuntime("org.postgresql:postgresql:42.7.2")
	liquibaseRuntime("info.picocli:picocli:4.6.3")
	implementation("org.apache.tomcat:tomcat-jsp-api:10.1.20")
	implementation("javax.servlet.jsp:jsp-api:2.1")
	implementation("javax.mail:javax.mail-api:1.6.2")
	implementation("org.webjars:stomp-websocket:2.3.4")
	implementation("org.webjars:sockjs-client:1.5.1")
	implementation("org.webjars:jquery:3.6.0")
	implementation("org.webjars:bootstrap:4.6.0")
	implementation("org.webjars:webjars-locator-core:0.46")
	implementation("io.minio:minio:8.5.10")
	implementation("net.minidev:json-smart:2.4.7")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	implementation("net.bytebuddy:byte-buddy:1.12.23")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(false)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}
jacoco {
	toolVersion = "0.8.8"
	reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.5".toBigDecimal()
			}
		}
	}
}

var props = Properties()
props.load(file("src/main/resources/liquibase.properties").inputStream())

liquibase {
	activities.register("main") {
		arguments = mapOf(
				"changeLogFile" to props.get("change-log-file"),
				"url" to props.get("url"),
				"username" to props.get("username"),
				"password" to props.get("password"),
				"driver" to props.get("driver-class-name")
		)
	}
}