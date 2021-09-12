package com.jpragma.oms

import io.micronaut.http.annotation.*


@Controller("/oms")
class OmsController(
    private val omsService: OmsService
) {

    @Post("/place")
    fun placeOrder(@Body orderData: Order): String {
        val order:Order = orderData.copy(orderId = Order.randomOrderId())
        omsService.placeOrder(order)
        return order.orderId
    }

    @Put("/accept")
    fun acceptOrder(@QueryValue("orderId") orderId: String): String {
        omsService.acceptOrder(orderId)
        return "Order $orderId has been accepted"
    }

    @Put("/reject")
    fun rejectOrder(@QueryValue("orderId") orderId: String): String {
        omsService.rejectOrder(orderId)
        return "Order $orderId has been rejected"
    }

    @Put("/fulfill")
    fun fulfillOrder(@QueryValue("orderId") orderId: String): String {
        omsService.fulfillOrder(orderId)
        return "Order $orderId has been fulfilled"
    }

    @Get("/order/current")
    fun listOngoingOrders(): List<String> {
        return omsService.listOngoingOrders()
    }

}