plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    id("org.jetbrains.kotlin.kapt") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    id("io.micronaut.application") version "2.0.3" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.30"
}

allprojects {
    version = "0.1"
    group = "com.jpragma"
    repositories {
        mavenCentral()
    }
}

val kotlinVersion= project.properties["kotlinVersion"]

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
        implementation("io.temporal:temporal-sdk:1.0.9")
        runtimeOnly("ch.qos.logback:logback-classic")
    }
    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "13"
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "13"
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
    }
}