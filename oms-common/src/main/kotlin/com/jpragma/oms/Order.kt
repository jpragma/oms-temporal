package com.jpragma.oms

import java.util.*

data class Order(
    val orderId: String, // TODO Replace with inline value class once its serialization is supported by Jackson
    val customerId: String,
    val items: List<OrderItem>,
    var status: OrderStatus = OrderStatus.NONE
) {
    companion object {
        fun randomOrderId(): String {
            return UUID.randomUUID().toString()
        }
    }
}

data class OrderItem(
    val itemId: String,
    val description: String,
    val price: Double,
    val amount: Double
)

enum class OrderStatus {
    NONE, PLACED, WAITING_APPROVAL, APPROVED, REJECTED, FULFILLED;

    fun approvalResolved(): Boolean {
        return this == APPROVED || this == REJECTED
    }
}