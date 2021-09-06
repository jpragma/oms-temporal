plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
}

micronaut {
    runtime("none")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.jpragma.*")
    }
}

dependencies {
    implementation(project(":oms-common"))
    implementation("io.micronaut:micronaut-runtime")
}

application {
    mainClass.set("com.jpragma.oms.WorkerApplicationKt")
}