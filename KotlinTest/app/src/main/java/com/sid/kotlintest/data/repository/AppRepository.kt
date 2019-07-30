package com.sid.kotlintest.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sid.kotlintest.data.db.dto.Order
import com.sid.kotlintest.data.db.dto.Product
import com.sid.kotlintest.model.ProductsList
import java.util.*

class AppRepository {
    val application: Application
    val productsLiveData: MutableLiveData<MutableList<Product>>

    constructor(application: Application) {
        this.application = application
        this.productsLiveData = MutableLiveData()
    }

    fun downloadProducts(type:String){
        /**
         * Todo: Write code to download products from network datasource. For now reading local generated data.
         */

        // Temp: Parsing JSON response from Assets
        val jsonText: String =
            application.applicationContext.assets.open("${type.replace(" ","_")}.json").bufferedReader().use { it.readText() }
        var objectMapper: ObjectMapper = jacksonObjectMapper()

        var productsList: ProductsList = objectMapper.readValue(jsonText, ProductsList::class.java)
        productsList?.let { productsLiveData.postValue(it.productsList) }
    }

    fun placeOrder(order: Order): Boolean {
        // Todo: Write code to place orders
        // Just for testing... Taking current Time in Millis as order id
        order.id = Calendar.getInstance().timeInMillis

        // Store Order Details in DB
        return order.id > 0
    }
}