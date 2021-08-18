package com.jpragma.oms

import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import javax.inject.Singleton

@Singleton
class OmsService(
    private val workflowServiceStubs: WorkflowServiceStubs,
    private val workflowClient: WorkflowClient
) {

    fun placeOrder(order: Order) {
        val workflowId = order.orderId
        val orderWorkflow = createWorkflow(workflowId)
        WorkflowClient.start { orderWorkflow.startOrderWorkflow(order) }
    }

    fun acceptOrder(workflowId: String) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, workflowId)
        orderWorkflow.signalOrderAccepted()
    }

    fun deliverOrder(workflowId: String) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, workflowId)
        orderWorkflow.signalOrderDelivered()
    }

    private fun createWorkflow(workflowId: String): OrderWorkflow {
        val options = WorkflowOptions.newBuilder()
            .setTaskQueue(OrderWorkflow.QUEUE_NAME)
            .setWorkflowId(workflowId)
            .build()
        return workflowClient.newWorkflowStub(OrderWorkflow::class.java, options)
    }
}