package com.jpragma.oms

import io.micronaut.context.annotation.Factory
import io.temporal.client.WorkflowClient
import jakarta.inject.Singleton


@Factory
class DriverBeanFactory {
    @Singleton
    fun workflowClient(): WorkflowClient {
        return WorkflowClientFactory.createWorkflowClient("127.0.0.1:7233")
    }
}