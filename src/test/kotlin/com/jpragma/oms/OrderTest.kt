package com.jpragma.oms

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest
internal class OrderTest {

    @Inject
    private lateinit var mapper:ObjectMapper

    @Test
    fun `can deserialize Order object`() {
        mapper.registerModule(KotlinModule())
        val json = """{
            "orderId": "85296db8-dad0-445e-9f9c-4bfd5e6af542",
            "items": [
              {
                "itemId": "wvb1",
                "description": "White Vienna",
                "price": 5.45,
                "amount": 2
              }
            ]
          }""".trimIndent()
        val order: Order = mapper.readValue(json)
        assertNotNull(order)
    }
}