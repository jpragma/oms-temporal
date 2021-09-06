package com.jpragma.oms

import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import jakarta.inject.Singleton

@Singleton
class OmsService(
    private val workflowClient: WorkflowClient
) {

    fun placeOrder(order: Order) {
        val orderWorkflow = createWorkflow(order.orderId)
        WorkflowClient.start { orderWorkflow.startOrderWorkflow(order) }
    }

    fun acceptOrder(orderId: OrderId) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, orderId.toWorkflowId())
        orderWorkflow.signalOrderAccepted()
    }

    private fun createWorkflow(orderId: OrderId): OrderWorkflow {
        val options = WorkflowOptions.newBuilder()
            .setTaskQueue(OrderWorkflow.ORDER_QUEUE_NAME)
            .setWorkflowId(orderId.toWorkflowId())
            .build()
        return workflowClient.newWorkflowStub(OrderWorkflow::class.java, options)
    }
}