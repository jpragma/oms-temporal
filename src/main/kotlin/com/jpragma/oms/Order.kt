package com.jpragma.oms

// TODO Convert to data class and register kotlin jackson module
class Order {
    constructor()

    constructor(orderId: String, items: List<OrderItem>) {
        this.orderId = orderId
        this.items = items
    }

    lateinit var orderId: String
    lateinit var items: List<OrderItem>
    var status: String = ""
}

class OrderItem {
    lateinit var itemId: String
    lateinit var description: String
    var price: Double = 0.0
    var amount: Double = 0.0
}