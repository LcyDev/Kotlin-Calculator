plugins {
    alias(libs.plugins.kotlin)
}

group = providers.gradleProperty("group").get()
version = providers.gradleProperty("proyect_version").get()
description = providers.gradleProperty("proyect_description").get()

val javaVersion: Int = providers.gradleProperty("java_version").get().toInt()

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.jackson.dataformat.yml)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.klogging)
}

tasks.withType<JavaCompile> {
    sourceCompatibility = javaVersion.toString()
    targetCompatibility = javaVersion.toString()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}