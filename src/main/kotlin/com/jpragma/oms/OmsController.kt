package com.jpragma.oms

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/oms")
class OmsController(
    private val omsService: OmsService
) {

    @Get("/place")
    fun placeOrder(@QueryValue("orderId") orderId: String): String {
        omsService.placeOrder(orderId)
        return "Order $orderId has been placed"
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