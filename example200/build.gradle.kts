plugins {
    java
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "pl.mikbac"
version = "0.0.1-SNAPSHOT"
description = "example200"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2025.0.0-M7"))

    implementation("org.springframework.boot:spring-boot-starter")

    implementation("io.projectreactor:reactor-core")
    implementation("org.redisson:redisson:4.3.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.21.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.21")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.1")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("io.projectreactor:reactor-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
