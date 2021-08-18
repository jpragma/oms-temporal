package com.jpragma.oms

import javax.inject.Singleton

@Singleton
class OrderActivityImpl : OrderActivity {
    override fun placeOrder() {
        println("*** Order placed")
    }

    override fun orderAccepted() {
        println("*** Order accepted")
    }

    override fun orderDelivered() {
        println("*** Order delivered")
    }
}