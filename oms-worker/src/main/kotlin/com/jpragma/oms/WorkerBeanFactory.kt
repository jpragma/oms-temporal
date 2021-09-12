package com.jpragma.oms

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import io.temporal.client.WorkflowClient
import io.temporal.worker.WorkerFactory
import jakarta.inject.Singleton

@Factory
class WorkerBeanFactory {
    @Singleton
    fun workflowClient(objectMapper: ObjectMapper, @Value("\${temporal.server.address}") temporalServerAddress: String): WorkflowClient {
        return WorkflowClientFactory.createWorkflowClient(objectMapper, temporalServerAddress)
    }

    @Singleton
    fun workerFactory(workflowClient: WorkflowClient): WorkerFactory {
        return WorkerFactory.newInstance(workflowClient)
    }
}