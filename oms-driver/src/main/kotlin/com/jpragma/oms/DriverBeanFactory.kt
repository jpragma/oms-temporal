package com.jpragma.oms

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import io.temporal.client.WorkflowClient
import jakarta.inject.Singleton


@Factory
class DriverBeanFactory {
    @Singleton
    fun workflowClient(@Value("\${temporal.server.address}") temporalServerAddress: String): WorkflowClient {
        return WorkflowClientFactory.createWorkflowClient(temporalServerAddress)
    }
}