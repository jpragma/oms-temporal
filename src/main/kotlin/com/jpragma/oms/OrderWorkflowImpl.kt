package com.jpragma.oms

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class OrderWorkflowImpl : OrderWorkflow {
    private val orderActivity = createOrderActivityStub()
    private var isOrderAccepted:Boolean = false
    private var isOrderDelivered:Boolean = false
    private lateinit var order:Order

    override fun startOrderWorkflow(order: Order) {
        order.status = "Placed"
        this.order = order
        println("Order ${order.orderId} is placed")
        orderActivity.placeOrder()

        println("Waiting for order to be accepted...")
        Workflow.await { isOrderAccepted }

        println("Waiting for order to be delivered...")
        Workflow.await { isOrderDelivered }
    }

    override fun signalOrderAccepted() {
        println("Received a signal to accept order ${order.orderId}")
        orderActivity.orderAccepted(this.order)
        order.status = "Accepted"
        isOrderAccepted = true
    }

    override fun signalOrderDelivered() {
        orderActivity.orderDelivered(this.order)
        isOrderDelivered = true
    }

    override fun showOrder(): Order {
        return order
    }

    fun createOrderActivityStub(): OrderActivity {
        return Workflow.newActivityStub(OrderActivity::class.java, defaultActivityOptions())
    }

    private fun defaultActivityOptions(): ActivityOptions {
        val retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2.0)
            .setMaximumAttempts(50_000)
            .build()
        return ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryOptions)
            .build()

    }
}