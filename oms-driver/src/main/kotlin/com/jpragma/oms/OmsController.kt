package com.jpragma.oms

import io.micronaut.http.annotation.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@ExperimentalSerializationApi
@Controller("/oms")
class OmsController(
    private val omsService: OmsService
) {

    @Post("/place")
    fun placeOrder(@Body orderItemsJson: String): String {
        val order:Order = Json.decodeFromString<Order>(orderItemsJson).copy(orderId = OrderId.randomOrderId())
        omsService.placeOrder(order)
        return order.orderId.toWorkflowId()
    }

    @Put("/accept")
    fun acceptOrder(@QueryValue("orderId") orderId: String): String {
        omsService.acceptOrder(OrderId(orderId))
        return "Order $orderId has been accepted"
    }

    @Put("/reject")
    fun rejectOrder(@QueryValue("orderId") orderId: String): String {
        omsService.rejectOrder(OrderId(orderId))
        return "Order $orderId has been rejected"
    }

    @Put("/fulfill")
    fun fulfillOrder(@QueryValue("orderId") orderId: String): String {
        omsService.fulfillOrder(OrderId(orderId))
        return "Order $orderId has been fulfilled"
    }

    @Get("/order/current")
    fun listOngoingOrders(): List<String> {
        return omsService.listOngoingOrders().map { it.toWorkflowId() }
    }

}