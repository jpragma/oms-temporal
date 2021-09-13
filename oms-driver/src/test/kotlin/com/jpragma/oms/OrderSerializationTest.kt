package com.jpragma.oms

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
internal class OrderSerializationTest {

    @Inject
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `can deserialize Order object using jackson`() {
        val json = """{
            "orderId": "o123",
            "customerId": "c987",
            "items": [
              {
                "itemId": "i987",
                "description": "BLT sandwich",
                "price": 8.45,
                "amount": 2
              }
            ]
          }""".trimIndent()
        val order = objectMapper.readValue(json, Order::class.java)
        val expectedOrder = Order(
            orderId = "o123",
            customerId = "c987",
            items = listOf(OrderItem("i987", "BLT sandwich", 8.45, 2.0))
        )

        assertEquals(expectedOrder, order)
    }


}