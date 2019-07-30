package com.sid.kotlintest.data.db.dto

import android.os.Parcel
import android.os.Parcelable

class OrderItem() : Parcelable{
    var product:Product? = null
    var quantity:Int = 0

    constructor(parcel: Parcel) : this() {
        product = parcel.readParcelable(Product::class.java.classLoader)
        quantity = parcel.readInt()
    }

    constructor(product: Product, quantity: Int) : this() {
        this.product = product
        this.quantity = quantity
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(product, flags)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getTotal():Double{
        return product?.price?.times(quantity) ?: 0.0
    }

    companion object CREATOR : Parcelable.Creator<OrderItem> {
        override fun createFromParcel(parcel: Parcel): OrderItem {
            return OrderItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderItem?> {
            return arrayOfNulls(size)
        }
    }
}