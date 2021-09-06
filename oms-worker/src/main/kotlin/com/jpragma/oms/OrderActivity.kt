package com.jpragma.oms

import com.jpragma.oms.Order
import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface OrderActivity {
    @ActivityMethod
    fun placeOrder()
    @ActivityMethod
    fun orderAccepted(order: Order)
}