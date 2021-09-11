package com.jpragma.oms

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.jvm.internal.Reflection
import kotlin.reflect.full.createType

@ExperimentalSerializationApi
internal class OrderSerializationTest {

    @Test
    fun `can deserialize Order object using kotlin_serialization`() {
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
        val order = Json.decodeFromString<Order>(json)
        val expectedOrder = Order(
            orderId = OrderId("o123"),
            customerId = CustomerId("c987"),
            items = listOf(OrderItem("i987", "BLT sandwich", 8.45, 2.0))
        )
        assertEquals(expectedOrder, order)
    }

    @Test
    fun `serialize without specifying exact type`() {
        val order = Order(
            orderId = OrderId("o123"),
            customerId = CustomerId("c987"),
            items = listOf(OrderItem("i987", "BLT sandwich", 8.45, 2.0))
        )
        val serialized = serializeAnyObject(order.items)
        println(serialized)
        assertNotNull(serialized)
    }

    private fun serializeAnyObject(value: Any):String {
        return Json.encodeToString(serializerForClass(value.javaClass), value)
    }

    private fun <T : Any> serializerForClass(valueClass: Class<T>): KSerializer<T> {
        val kClass = Reflection.createKotlinClass(valueClass)
        val kType = kClass.createType()
        return serializer(kType) as KSerializer<T>
    }

}