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
        val orderItems:List<OrderItem> = Json.decodeFromString(orderItemsJson)
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

    @Get("/order/current")
    fun listOngoingOrders(): List<String> {
        return omsService.listOngoingOrders().map { it.toWorkflowId() }
    }

}