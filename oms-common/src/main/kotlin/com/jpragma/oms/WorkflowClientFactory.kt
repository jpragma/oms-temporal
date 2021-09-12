package com.jpragma.oms

import com.fasterxml.jackson.databind.ObjectMapper
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowClientOptions
import io.temporal.common.converter.*
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.serviceclient.WorkflowServiceStubsOptions

class WorkflowClientFactory {
    companion object {
        fun createWorkflowClient(objectMapper: ObjectMapper, temporalServerAddress: String): WorkflowClient {
            val stubsOptions = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalServerAddress).build()
            val stubs = WorkflowServiceStubs.newInstance(stubsOptions)
            val dataConverter = customPayloadConverter(objectMapper)
            val clientOptions = WorkflowClientOptions.newBuilder()
                .setNamespace("default")
                .setDataConverter(dataConverter)
                .build()
            return WorkflowClient.newInstance(stubs, clientOptions)
        }

        private fun customPayloadConverter(objectMapper: ObjectMapper): DataConverter {
            // Order is important as the first converter that can convert the payload is used
            return DefaultDataConverter(
                NullPayloadConverter(),
                ByteArrayPayloadConverter(),
                ProtobufJsonPayloadConverter(),
                JacksonJsonPayloadConverter(objectMapper)
            )
        }

    }
}