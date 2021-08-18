package com.jpragma.oms

import javax.inject.Singleton

@Singleton
class OrderActivityImpl : OrderActivity {
    override fun placeOrder() {
        println("*** Order placed")
    }

    override fun orderAccepted(order: Order) {
        println("*** Order ${order.orderId} accepted")
    }

    override fun orderDelivered(order: Order) {
        println("*** Order ${order.orderId} delivered")
    }
}