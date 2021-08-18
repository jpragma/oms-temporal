package com.jpragma.oms

import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface OrderWorkflow {
    companion object {
        val QUEUE_NAME = "Customer_Order"
    }

    @WorkflowMethod
    fun startOrderWorkflow()

    @SignalMethod
    fun signalOrderAccepted()

    @SignalMethod
    fun signalOrderDelivered()
}