package com.jpragma.oms

import io.micronaut.context.annotation.Factory
import io.temporal.client.WorkflowClient
import io.temporal.worker.WorkerFactory
import jakarta.inject.Singleton


@Factory
class OrderBeanFactory {

    @Singleton
    fun workflowClient(): WorkflowClient {
        return WorkflowClientFactory.createWorkflowClient("127.0.0.1:7233")
    }
    
    @Singleton
    fun workerFactory(workflowClient: WorkflowClient): WorkerFactory {
        return WorkerFactory.newInstance(workflowClient)
    }
}