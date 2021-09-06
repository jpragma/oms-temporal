package com.jpragma.oms

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import io.temporal.client.WorkflowNotFoundException
import jakarta.inject.Singleton

@Produces
@Singleton
class WorkflowNotFoundExceptionHandler(private val errorResponseProcessor: ErrorResponseProcessor<Any>) :
    ExceptionHandler<WorkflowNotFoundException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: WorkflowNotFoundException?): HttpResponse<*> {
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage("Workflow ${exception?.execution?.workflowId} does not exist")
                .build(),
            HttpResponse.badRequest<Any>()
        )
    }
}