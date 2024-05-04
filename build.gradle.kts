plugins {
    kotlin("jvm") version "1.9.22"
}

group = "quark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.netty:netty-all:4.1.100.Final")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.22")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Jar> {
    enabled = true
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations.compileClasspath)

    manifest {
        attributes["Main-Class"] = "quark.MainKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}