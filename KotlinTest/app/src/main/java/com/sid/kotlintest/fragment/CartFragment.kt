package com.sid.kotlintest.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.sid.kotlintest.R
import com.sid.kotlintest.adapter.CartAdapter
import com.sid.kotlintest.data.db.dto.OrderItem
import com.sid.kotlintest.data.db.dto.Product
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    val orderItems:MutableList<OrderItem> = mutableListOf()
    var listener:Listener? = null
    lateinit var adapter: CartAdapter
    lateinit var rootView:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(activity is Listener){
            listener = activity as Listener
        }
        adapter = CartAdapter(context!!, orderItems)
        rootView = inflater.inflate(R.layout.fragment_cart, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cart_header_close.setOnClickListener {
            // Close Bottom Sheet
            listener?.closeCart()
        }

        cart_checkout.setOnClickListener {
            listener?.placeOrder()
        }
        var layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        cart_items_recycler_view.layoutManager = layoutManager

        var decoration = DividerItemDecoration(context, layoutManager.orientation)
        cart_items_recycler_view.addItemDecoration(decoration)
        cart_items_recycler_view.adapter = adapter

        setTotalNumberOfItems()
        setCartTotal()
    }

    fun onCartChanged(items:MutableList<OrderItem>){
        this.orderItems.clear()
        this.orderItems.addAll(items)
        this.adapter.notifyDataSetChanged()
        setTotalNumberOfItems()
        setCartTotal()
    }

    private fun setTotalNumberOfItems(){
        cart_count.text = String.format(getString(R.string.cart_count_text),orderItems?.size)
    }

    private fun setCartTotal(){
        var total:Double = 0.0
        orderItems?.forEach {
            total += it.product?.price?.times(it.quantity) ?: 0.0
        }

        cart_total_value.text = String.format(getString(R.string.cart_total_value),total)
    }

    fun setAlpha(alpha:Float){
        rootView.alpha = alpha
    }

    interface Listener{
        fun closeCart()
        fun placeOrder()
        fun changeItemOrder(orderItem: OrderItem)
    }
}
