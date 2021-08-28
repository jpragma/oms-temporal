package com.jpragma.oms

data class Order(
    val orderId: String,
    val items: List<OrderItem>,
    var status: String = ""
)

data class OrderItem(
    val itemId: String,
    val description: String,
    val price: Double,
    val amount: Double
)