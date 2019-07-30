package com.sid.kotlintest.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.sid.kotlintest.data.db.dto.Product

class ProductsList{
    @JsonProperty("products")
    var productsList: MutableList<Product>

    constructor(productsList: MutableList<Product>) {
        this.productsList = productsList
    }
}