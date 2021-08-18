package com.jpragma.oms

import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface OrderWorkflow {
    companion object {
        val QUEUE_NAME = "Customer_Order"
    }

    @WorkflowMethod
    fun startOrderWorkflow(order: Order)

    @SignalMethod
    fun signalOrderAccepted()

    @SignalMethod
    fun signalOrderDelivered()

    @QueryMethod
    fun showOrder():Order
}