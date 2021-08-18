package com.jpragma.oms

import io.micronaut.context.annotation.Factory
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowClientOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.serviceclient.WorkflowServiceStubsOptions
import io.temporal.worker.WorkerFactory
import javax.inject.Singleton


@Factory
class OrderBeanFactory {

    @Singleton
    fun workflowServiceStub(): WorkflowServiceStubs {
        return WorkflowServiceStubs.newInstance(
            WorkflowServiceStubsOptions.newBuilder().setTarget("127.0.0.1:7233").build()
        )
    }
    
    @Singleton
    fun workflowClient(workflowServiceStubs:WorkflowServiceStubs): WorkflowClient {
        return WorkflowClient.newInstance(workflowServiceStubs,
            WorkflowClientOptions.newBuilder().setNamespace("default").build()
        )
    }
    
    @Singleton
    fun workerFactory(workflowClient: WorkflowClient): WorkerFactory {
        return WorkerFactory.newInstance(workflowClient)
    }
}