package com.sid.kotlintest.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sid.kotlintest.R
import com.sid.kotlintest.adapter.ProductListAdapter
import com.sid.kotlintest.data.db.dto.OrderItem
import com.sid.kotlintest.data.db.dto.Product
import com.sid.kotlintest.data.viewModel.HomeViewModel
import com.sid.kotlintest.fragment.CartFragment
import com.sid.kotlintest.fragment.ProductFragment
import com.sid.kotlintest.utils.AppUtils

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*

class HomeActivity : AppCompatActivity(), ProductFragment.Listener, CartFragment.Listener,AdapterView.OnItemSelectedListener, ViewPager.OnPageChangeListener {

    lateinit var viewModel: HomeViewModel
    lateinit var adapter: ProductListAdapter
    lateinit var cartFragment:CartFragment
    lateinit var sheetBehavior: BottomSheetBehavior<View>
    var productsList:MutableList<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_toolbar)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        adapter = ProductListAdapter(supportFragmentManager)
        cartFragment = supportFragmentManager.findFragmentById(R.id.bottomsheet_fragment) as CartFragment
        sheetBehavior = BottomSheetBehavior.from(bottomsheet_parent)
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, slidePercent: Float) {
                setUpBottomSheet(slidePercent)
            }

            override fun onStateChanged(p0: View, p1: Int) {
                Log.d("Test","onStateChanged - "+p1)
            }

        })
        home_content_type_spinner.onItemSelectedListener = this

        home_content_viewpager.adapter = adapter
        home_content_viewpager.addOnPageChangeListener(this)

        supportActionBar?.title = null
        supportActionBar?.elevation = 0f

        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel?.productsLiveData?.observe(this, Observer {
            adapter?.setProducts(it)
            productsList = it
            applyGradient(0)
        })

        viewModel?.cartItems?.observe(this, Observer {
            cartFragment.onCartChanged(it.values.toMutableList())
        })

        home_toolbar_cart.setOnClickListener(View.OnClickListener {
            openCart()
        })

        setUpBottomSheet(0f)
    }

    private fun setUpBottomSheet(progress:Float){
        bottomsheet_teaser_title.alpha = 1.0f - progress
        cartFragment.setAlpha(progress)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        applyGradient(position)
    }

    private fun applyGradient(position: Int){
        var product = productsList?.get(position)
        product?.color?.let { AppUtils.applyGradient(home_parent, it)}
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.downloadProducts(getType(position))
    }

    private fun getType(position:Int):String{
        var types = resources.getStringArray(R.array.types)
        return types[position]
    }

    override fun onStart() {
        super.onStart()
        viewModel?.downloadProducts(getType(0))
    }

    override fun onAddItemToCart(product: Product?, count: Int) {
        viewModel.addItemToCart(OrderItem(product as Product,count))
        Toast.makeText(this,"Item added to cart",Toast.LENGTH_SHORT).show()
    }

    override fun changeItemOrder(orderItem: OrderItem) {
        viewModel.changeCartItemOrder(orderItem)
    }

    override fun closeCart() {
        setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    fun openCart(){
        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED)
    }
    private fun setBottomSheetState(state:Int){
        sheetBehavior.state = state
    }

    override fun placeOrder() {
        if(viewModel.placeOrder()){
            Toast.makeText(this,"Placed Order Successfully",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Failed to place an order",Toast.LENGTH_SHORT).show()
        }
    }
}
