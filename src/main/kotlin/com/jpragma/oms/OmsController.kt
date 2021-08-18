package com.jpragma.oms

import io.micronaut.http.annotation.*
import java.util.*

@Controller("/oms")
class OmsController(
    private val omsService: OmsService
) {

    @Post("/place")
    fun placeOrder(@Body orderItems: List<OrderItem>): String {
        val orderId = UUID.randomUUID().toString()
        val order = Order(orderId, orderItems)
        omsService.placeOrder(order)
        return orderId
    }

    @Get("/accept")
    fun acceptOrder(@QueryValue("orderId") orderId: String): String {
        omsService.acceptOrder(orderId)
        return "Order $orderId has been accepted"
    }

    @Get("/deliver")
    fun deliverOrder(@QueryValue("orderId") orderId: String): String {
        omsService.deliverOrder(orderId)
        return "Order $orderId has been delivered"
    }

}