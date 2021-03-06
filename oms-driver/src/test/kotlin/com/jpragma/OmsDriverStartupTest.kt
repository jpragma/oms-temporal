package com.jpragma
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class OmsDriverStartupTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun `application can start`() {
        Assertions.assertTrue(application.isRunning)
    }

}