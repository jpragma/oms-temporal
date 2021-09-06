package com.jpragma.oms

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Order(
    val orderId: OrderId,
    val items: List<OrderItem>,
    var status: OrderStatus = OrderStatus.NONE
)

@Serializable
data class OrderItem(
    val itemId: String,
    val description: String,
    val price: Double,
    val amount: Double
)

@Serializable
@JvmInline
value class OrderId(private val v:String) {
    companion object {
        fun randomOrderId():OrderId {
            return OrderId(UUID.randomUUID().toString())
        }
    }
    fun toWorkflowId(): String {
        return v
    }
}

enum class OrderStatus {
    NONE, PLACED, ACCEPTED, IN_PROGRESS, READY, IN_DELIVERY, DONE
}