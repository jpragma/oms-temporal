package com.jpragma.oms

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class OrderWorkflowImpl : OrderWorkflow {
    private val orderActivity = createOrderActivityStub()
    private var order: Order? = null

    override fun processOrder(order: Order) {
        this.order = order.apply { status = OrderStatus.PLACED }

        if (orderActivity.containsRestrictedItems(order.items)) {
            orderActivity.requestApproval(order)
            order.status = OrderStatus.WAITING_APPROVAL
            Workflow.await { order.status.approvalResolved()  }
        }
        orderActivity.sendOrderForFulfilment(order)
        Workflow.await { order.status == OrderStatus.FULFILLED }

        orderActivity.sendEmailOrderDone(order.customerId)
    }

    override fun signalOrderApproval(approved: Boolean) {
        order?.run {
            if (!approved) {
                status = OrderStatus.REJECTED
                orderActivity.sendEmailOrderRejected(customerId)
            } else {
                status = OrderStatus.APPROVED
            }
        }
    }

    override fun signalOrderFulfilled() {
        order?.run { this.status = OrderStatus.FULFILLED }
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