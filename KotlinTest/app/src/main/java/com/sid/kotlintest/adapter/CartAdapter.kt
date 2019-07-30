package com.sid.kotlintest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sid.kotlintest.R
import com.sid.kotlintest.data.db.dto.Order
import com.sid.kotlintest.data.db.dto.OrderItem
import kotlinx.android.synthetic.main.cart_item.view.*

class CartViewHolder(var context: Context, var view:View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    fun setData(item:OrderItem){
        view.cart_item_title.text = item?.product?.title
        view.cart_item_price.text = String.format(context.getString(R.string.cart_total_value),item?.getTotal())
        view.cart_item_quantity.text = item?.quantity.toString()
        view.cart_item_minus.setOnClickListener(this)
        view.cart_item_plus.setOnClickListener(this)

        Glide.with(context).load(item.product?.imageUrl).into(view.cart_item_icon)
    }

    override fun onClick(v: View?) = Toast.makeText(context,"Not functioning",Toast.LENGTH_SHORT).show()
}

class CartAdapter(val context:Context, private val itemsList:MutableList<OrderItem>): RecyclerView.Adapter<CartViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(context,view)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.setData(itemsList[position])
    }
}