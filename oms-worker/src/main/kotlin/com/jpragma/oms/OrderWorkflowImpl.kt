package com.jpragma.oms

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class OrderWorkflowImpl : OrderWorkflow {
    private val orderActivity = createOrderActivityStub()
    private var order: Order? = null

    override fun startOrderWorkflow(order: Order) {
        this.order = order.apply { status = OrderStatus.PLACED }

        orderActivity.placeOrder()

        Workflow.await { order.status == OrderStatus.ACCEPTED }
    }

    override fun signalOrderAccepted() {
        order?.let {
            orderActivity.orderAccepted(it)
            it.status = OrderStatus.ACCEPTED
        }
    }

    override fun showOrder(): Order {
        return order!!
    }

    private fun createOrderActivityStub(): OrderActivity {
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