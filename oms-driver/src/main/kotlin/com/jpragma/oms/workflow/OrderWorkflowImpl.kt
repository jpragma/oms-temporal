package com.jpragma.oms.workflow

import com.jpragma.oms.Order
import com.jpragma.oms.OrderStatus
import com.jpragma.oms.activity.OrderActivity
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class OrderWorkflowImpl : OrderWorkflow {
    private val orderActivity = createOrderActivityStub()
    private lateinit var order: Order

    override fun startOrderWorkflow(order: Order) {
        this.order = order.apply { status = OrderStatus.PLACED }

        orderActivity.placeOrder()

        Workflow.await { order.status == OrderStatus.ACCEPTED }
    }

    override fun signalOrderAccepted() {
        orderActivity.orderAccepted(this.order)
        order.status = OrderStatus.ACCEPTED
    }

    override fun showOrder(): Order {
        return order
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