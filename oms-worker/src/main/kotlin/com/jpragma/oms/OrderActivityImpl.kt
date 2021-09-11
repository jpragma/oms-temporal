package com.jpragma.oms

import jakarta.inject.Singleton


@Singleton
class OrderActivityImpl : OrderActivity {
    private val restrictedItemIds = setOf("a105", "a106")

    override fun containsRestrictedItems(order: Order): Boolean {
        // here we would call database or external service and check if requested items contains something restricted
        // mock implementation for a now
        return order.items.any { restrictedItemIds.contains(it.itemId) }
    }

    override fun requestApproval(order: Order) {
        println("*** Sending request to approve order with restricted items: ${order.items.map { it.itemId }}")
    }

    override fun sendOrderForFulfilment(order: Order) {
        println("*** Sending order ${order.orderId} for fulfilment")
    }

    override fun sendEmailOrderDone(customerId: CustomerId) {
        sendEmail(customerId, "Your order is done")
    }

    override fun sendEmailOrderRejected(customerId: CustomerId) {
        sendEmail(customerId, "Your order has been rejected")
    }

    private fun sendEmail(customerId: CustomerId, emailText: String) {
        println("Sending email for customer $customerId. Text: $emailText")
    }
}