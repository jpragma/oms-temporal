package com.jpragma.oms

import com.jpragma.oms.Order
import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface OrderActivity {
    @ActivityMethod
    fun containsRestrictedItems(items: List<OrderItem>): Boolean
    @ActivityMethod
    fun requestApproval(order: Order)
    @ActivityMethod
    fun sendOrderForFulfilment(order: Order)
    @ActivityMethod
    fun sendEmailOrderDone(customerId: CustomerId)
    @ActivityMethod
    fun sendEmailOrderRejected(customerId: CustomerId)
}