package com.jpragma.oms

import io.micronaut.context.annotation.Context
import io.temporal.worker.WorkerFactory
import javax.annotation.PostConstruct

@Context
class WorkerRegistration {
    @PostConstruct
    fun registerWorker(workerFactory: WorkerFactory, orderActivity: OrderActivity) {
        val worker = workerFactory.newWorker(OrderWorkflow.QUEUE_NAME)
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl::class.java)
        worker.registerActivitiesImplementations(orderActivity)
        workerFactory.start()
    }

}