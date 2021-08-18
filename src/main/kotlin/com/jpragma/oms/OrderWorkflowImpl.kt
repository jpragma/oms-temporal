package com.jpragma.oms

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class OrderWorkflowImpl : OrderWorkflow {
    private val orderActivity = createOrderActivityStub()
    private var isOrderAccepted:Boolean = false
    private var isOrderDelivered:Boolean = false

    override fun startOrderWorkflow() {
        orderActivity.placeOrder()

        println("Waiting for order to be accepted...")
        Workflow.await { isOrderAccepted }

        println("Waiting for order to be delivered...")
        Workflow.await { isOrderDelivered }
    }

    override fun signalOrderAccepted() {
        orderActivity.orderAccepted()
        isOrderAccepted = true
    }

    override fun signalOrderDelivered() {
        orderActivity.orderDelivered()
        isOrderDelivered = true
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