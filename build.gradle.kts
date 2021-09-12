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

val kotlinVersion=project.properties.get("kotlinVersion")

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

dependencies {
//    kapt("io.micronaut.data:micronaut-data-processor")
//    implementation("io.micronaut:micronaut-http-client")
//    implementation("io.micronaut:micronaut-runtime")
//    implementation("io.micronaut.data:micronaut-data-jdbc")
//    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
//    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
//    implementation("javax.annotation:javax.annotation-api")
//    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
//    runtimeOnly("ch.qos.logback:logback-classic")
//    runtimeOnly("org.postgresql:postgresql")
//    testImplementation("org.testcontainers:junit-jupiter")
//    testImplementation("org.testcontainers:postgresql")
//    testImplementation("org.testcontainers:testcontainers")
//    implementation("io.micronaut:micronaut-validation")
//
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")



}

//java {
//    sourceCompatibility = JavaVersion.toVersion("11")
//}