package com.jpragma.oms

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class OrderTest {

    @Test
    fun `can deserialize Order object using kotlin_serialization`() {
        val json = """{
            "orderId": "o123",
            "items": [
              {
                "itemId": "i987",
                "description": "BLT sandwich",
                "price": 8.45,
                "amount": 2
              }
            ]
          }""".trimIndent()
        val order = Json.decodeFromString<Order>(json)
        val expectedOrder = Order(
            orderId = OrderId("o123"),
            items = listOf(OrderItem("i987", "BLT sandwich", 8.45, 2.0))
        )
        assertEquals(expectedOrder, order)
    }
}