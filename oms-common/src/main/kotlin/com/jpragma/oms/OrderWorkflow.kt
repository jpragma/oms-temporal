package com.jpragma.oms

import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface OrderWorkflow {
    companion object {
        val ORDER_QUEUE_NAME = "Customer_Order"
    }

    @WorkflowMethod
    fun processOrder(order: Order)

    @SignalMethod
    fun signalOrderApproval(approved: Boolean)

    @SignalMethod
    fun signalOrderFulfilled()

    @QueryMethod
    fun showOrder(): Order
}