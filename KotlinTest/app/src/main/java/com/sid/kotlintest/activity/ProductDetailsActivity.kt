package com.sid.kotlintest.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.alpha
import com.bumptech.glide.Glide
import com.sid.kotlintest.R
import com.sid.kotlintest.data.db.dto.Product
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {
    companion object {
        val PRODUCT_KEY = "com.sid.kotlintest.activity.ProductDetailsActivity.PRODUCT_KEY"
    }

    var product:Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        product = intent?.getParcelableExtra<Product>(PRODUCT_KEY)

        product_details_background_view.setBackgroundColor(Color.parseColor(product?.color))
        Glide.with(this).load(product?.imageUrl).into(product_details_imageview)
    }
}
