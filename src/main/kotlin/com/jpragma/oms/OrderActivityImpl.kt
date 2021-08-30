package com.jpragma.oms

import jakarta.inject.Singleton


@Singleton
class OrderActivityImpl : OrderActivity {
    override fun placeOrder() {
        println("*** Order placed")
    }

    override fun orderAccepted(order: Order) {
        println("*** Order ${order.orderId} accepted")
    }

}