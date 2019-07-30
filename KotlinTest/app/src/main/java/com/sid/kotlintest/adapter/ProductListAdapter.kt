package com.sid.kotlintest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sid.kotlintest.data.db.dto.Product
import com.sid.kotlintest.fragment.ProductFragment

class ProductListAdapter : FragmentPagerAdapter {
    val fragmentManager: FragmentManager
    val fragments: MutableList<Fragment> = mutableListOf()

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun setProducts(productsList: MutableList<Product>): Unit {
        // Create Fragments
        fragments.clear()
        for (product in productsList) {
            fragments.add(ProductFragment.newInstance(product))
        }
        notifyDataSetChanged()
    }


    /**
     * Overridden Function(s)
     */
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}