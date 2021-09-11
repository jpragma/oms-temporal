package com.jpragma.oms

import io.temporal.api.enums.v1.WorkflowExecutionStatus
import io.temporal.api.workflowservice.v1.ListWorkflowExecutionsRequest
import io.temporal.api.workflowservice.v1.ListWorkflowExecutionsResponse
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import jakarta.inject.Singleton

@Singleton
class OmsService(
    private val workflowClient: WorkflowClient
) {

    fun placeOrder(order: Order) {
        val orderWorkflow = createWorkflow(order.orderId)
        WorkflowClient.start { orderWorkflow.processOrder(order) }
    }

    fun acceptOrder(orderId: OrderId) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, orderId.toWorkflowId())
        orderWorkflow.signalOrderApproval(true)
    }

    fun rejectOrder(orderId: OrderId) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, orderId.toWorkflowId())
        orderWorkflow.signalOrderApproval(false)
    }

    fun fulfillOrder(orderId: OrderId) {
        val orderWorkflow = workflowClient.newWorkflowStub(OrderWorkflow::class.java, orderId.toWorkflowId())
        orderWorkflow.signalOrderFulfilled()
    }

    private fun createWorkflow(orderId: OrderId): OrderWorkflow {
        val options = WorkflowOptions.newBuilder()
            .setTaskQueue(OrderWorkflow.ORDER_QUEUE_NAME)
            .setWorkflowId(orderId.toWorkflowId())
            .build()
        return workflowClient.newWorkflowStub(OrderWorkflow::class.java, options)
    }

    fun listOngoingOrders():List<OrderId> {
        val workflowExecutionResponse:ListWorkflowExecutionsResponse = getExecutionResponse("""
            WorkflowType = 'OrderWorkflow' and
            ExecutionStatus = ${WorkflowExecutionStatus.WORKFLOW_EXECUTION_STATUS_RUNNING_VALUE}
        """.trimIndent())
        return workflowExecutionResponse.executionsList.map { it.execution.workflowId }.map { OrderId(it) }
    }

    private fun getExecutionResponse(query: String): ListWorkflowExecutionsResponse {
        val listWorkflowExecutionsRequest = ListWorkflowExecutionsRequest.newBuilder()
            .setNamespace("default") // todo inject
            .setQuery(query)
            .build()
        return workflowClient.workflowServiceStubs.blockingStub().listWorkflowExecutions(listWorkflowExecutionsRequest)
    }
}