package com.sid.kotlintest.data.db.dto

import java.util.*

open class Order{
    var id:Long = 0
    var total:Double = 0.0
    var orderItems:MutableList<OrderItem>? = null
    var tax:Double = 0.5
    var orderTime:Date = Calendar.getInstance().time
    var pickUpTime:Date = Calendar.getInstance().time
    var customerName:String? = null


}