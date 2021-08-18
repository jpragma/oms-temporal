package com.jpragma.oms

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface OrderActivity {
    @ActivityMethod
    fun placeOrder()
    @ActivityMethod
    fun orderAccepted()
    @ActivityMethod
    fun orderDelivered()
}