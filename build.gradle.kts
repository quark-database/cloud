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
    manifest {
        attributes("Main-Class" to "quark.MainKt")
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({ configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }})
}