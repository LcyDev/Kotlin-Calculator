plugins {
    kotlin("jvm") version "2.0.0"
}

group = "io.lwcl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
    implementation("io.klogging:klogging-jvm:0.5.14")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}