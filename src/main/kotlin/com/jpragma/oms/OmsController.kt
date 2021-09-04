package com.jpragma.oms

import io.micronaut.http.annotation.*


@Controller("/oms")
class OmsController(
    private val omsService: OmsService
) {

    @Post("/place")
    fun placeOrder(@Body orderItems: List<OrderItem>): String {
        val orderId = OrderId.randomOrderId()
        val order = Order(orderId, orderItems)
        omsService.placeOrder(order)
        return orderId.toWorkflowId()
    }

    @Put("/accept")
    fun acceptOrder(@QueryValue("orderId") orderId: String): String {
        omsService.acceptOrder(OrderId(orderId))
        return "Order $orderId has been accepted"
    }

}