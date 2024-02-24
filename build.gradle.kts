val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
}

group = "com.delminiusdevs"
version = "0.0.1"

application {
    mainClass.set("com.delminiusdevs.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
    implementation("commons-codec:commons-codec:1.14")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
}

task("stage") {
    dependsOn("shadowJar")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                80,
                8080,
                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))
    }
}
