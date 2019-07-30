package com.sid.kotlintest.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sid.kotlintest.data.db.dto.Order
import com.sid.kotlintest.data.db.dto.OrderItem
import com.sid.kotlintest.data.db.dto.Product
import com.sid.kotlintest.data.repository.AppRepository
import java.util.*

class HomeViewModel : AndroidViewModel {
    val repository:AppRepository
    val productsLiveData:MutableLiveData<MutableList<Product>>
    val cartItems: MutableLiveData<MutableMap<Int, OrderItem>>

    constructor(application: Application):super(application){
        repository = AppRepository(application)
        productsLiveData = repository.productsLiveData
        cartItems = MutableLiveData()
        if(cartItems.value == null) cartItems.value = mutableMapOf()
    }

    fun downloadProducts(type:String) = repository?.downloadProducts(type)
    fun addItemToCart(orderItem:OrderItem){
        var itemsMap = cartItems.value
        if(itemsMap?.containsKey(orderItem?.product?.id)!!){
            var removedItem = itemsMap?.remove(orderItem?.product?.id)
            removedItem?.quantity = removedItem!!.quantity + orderItem.quantity
            removedItem.product?.id?.let { itemsMap.put(it, removedItem) }
        } else {
            orderItem?.product?.id?.let { itemsMap.put(it, orderItem) }
        }
        cartItems.postValue(itemsMap)
    }

    fun changeCartItemOrder(orderItem: OrderItem){
        var itemsMap = cartItems.value
        if(itemsMap?.containsKey(orderItem?.product?.id)!!) {
            itemsMap?.remove(orderItem?.product?.id)
        }
        orderItem?.product?.id?.let { itemsMap.put(it, orderItem) }
        cartItems.postValue(itemsMap)
    }

    fun placeOrder():Boolean {
        return repository.placeOrder(createOrder())
    }

    private fun createOrder() : Order {
        var order = Order()
        order.customerName = "App User"
        order.orderItems = cartItems.value?.values?.toMutableList()
        order.orderTime = Calendar.getInstance().time
        var pickUpCalendar = Calendar.getInstance()
        pickUpCalendar.add(Calendar.HOUR, 2)
        order.pickUpTime = pickUpCalendar.time
        order.total = calculateOrderItemsTotal()
        order.tax = order.tax.times(0.1)
        return order
    }

    private fun calculateOrderItemsTotal(): Double{
        var total = 0.0
        cartItems.value?.values?.forEach {
            total += it.getTotal()
        }
        return total
    }
}