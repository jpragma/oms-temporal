package com.jpragma.temporal

import com.jpragma.oms.activity.OrderActivity
import com.jpragma.oms.OrderWorkflow
import com.jpragma.oms.OrderWorkflowImpl
import io.micronaut.context.annotation.Context
import io.temporal.worker.WorkerFactory
import javax.annotation.PostConstruct

@Context
class WorkerRegistration {
    @PostConstruct
    fun registerWorker(workerFactory: WorkerFactory, orderActivity: OrderActivity) {
        val worker = workerFactory.newWorker(OrderWorkflow.ORDER_QUEUE_NAME)
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl::class.java)
        worker.registerActivitiesImplementations(orderActivity)
        workerFactory.start()
    }

}