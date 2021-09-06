plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.jpragma.*")
    }
}

application {
    mainClass.set("com.jpragma.ApplicationKt")
}