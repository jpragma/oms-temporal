package com.jpragma.oms

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Factory
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowClientOptions
import io.temporal.common.converter.*
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.serviceclient.WorkflowServiceStubsOptions
import io.temporal.worker.WorkerFactory
import jakarta.inject.Singleton


@Factory
class OrderBeanFactory {

    @Singleton
    fun workflowServiceStub(): WorkflowServiceStubs {
        return WorkflowServiceStubs.newInstance(
            WorkflowServiceStubsOptions.newBuilder().setTarget("127.0.0.1:7233").build()
        )
    }

    private fun customPayloadConverter(objectMapper: ObjectMapper): DataConverter {
        // Order is important as the first converter that can convert the payload is used
        return DefaultDataConverter(
            NullPayloadConverter(),
            ByteArrayPayloadConverter(),
            ProtobufJsonPayloadConverter(),
            JacksonJsonPayloadConverter(objectMapper) // TODO REPLACE WITH Kotlin json serializer
        )
    }

    @Singleton
    fun workflowClient(workflowServiceStubs:WorkflowServiceStubs, objectMapper: ObjectMapper): WorkflowClient {
        val dataConverter = customPayloadConverter(objectMapper)
        return WorkflowClient.newInstance(workflowServiceStubs,
            WorkflowClientOptions.newBuilder()
                .setNamespace("default")
                .setDataConverter(dataConverter)
                .build()
        )
    }
    
    @Singleton
    fun workerFactory(workflowClient: WorkflowClient): WorkerFactory {
        return WorkerFactory.newInstance(workflowClient)
    }
}