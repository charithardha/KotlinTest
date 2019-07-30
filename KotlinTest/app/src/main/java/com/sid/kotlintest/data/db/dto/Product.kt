package com.sid.kotlintest.data.db.dto

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

class Product() : Parcelable{
    @JsonProperty("id") var id:Int? = -1
    @JsonProperty("title") var title:String? = null
    @JsonProperty("body") var body:String? = null
    @JsonProperty("price") var price:Double? = null
    @JsonProperty("") var color: String? = null
    @JsonProperty("imageUrl") var imageUrl:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
        body = parcel.readString()
        price = parcel.readValue(Double::class.java.classLoader) as? Double
        color = parcel.readString()
        imageUrl = parcel.readString()
    }


    constructor(id:Int,title: String, body: String, price: Double, color: String, imageUrl: String) : this() {
        this.id = id
        this.title = title
        this.body = body
        this.price = price
        this.color = color
        this.imageUrl = imageUrl
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeValue(price)
        parcel.writeString(color)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}