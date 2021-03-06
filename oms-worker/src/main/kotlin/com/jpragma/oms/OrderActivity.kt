package com.jpragma.oms

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface OrderActivity {
    @ActivityMethod
    fun containsRestrictedItems(order: Order): Boolean
    @ActivityMethod
    fun requestApproval(order: Order)
    @ActivityMethod
    fun sendOrderForFulfilment(order: Order)
    @ActivityMethod
    fun sendEmailOrderDone(customerId: String)
    @ActivityMethod
    fun sendEmailOrderRejected(customerId: String)
}