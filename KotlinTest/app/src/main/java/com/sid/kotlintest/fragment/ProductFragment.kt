package com.sid.kotlintest.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.sid.kotlintest.R
import com.sid.kotlintest.activity.HomeActivity
import com.sid.kotlintest.activity.ProductDetailsActivity
import com.sid.kotlintest.data.db.dto.Product
import kotlinx.android.synthetic.main.fragment_product.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(product: Product): ProductFragment = ProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PRODUCT_KEY, product)
            }
        }


        val PRODUCT_KEY: String = "com.sid.kotlintest.fragment.ProductFragment.PRODUCT"
    }

    var product: Product? = null
    var listener:Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(activity is Listener){
            listener = activity as Listener
        }

        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = arguments?.get(PRODUCT_KEY) as Product

        fragment_product_title.text = product?.title
        fragment_product_body.text = product?.body
        fragment_product_price.text = product?.price.toString()
        fragment_product_background_view.setBackgroundColor(Color.parseColor(product?.color))
        Glide.with(activity!!).load(product?.imageUrl).into(fragment_product_imageView)

        fragment_product_imageView.setOnClickListener(View.OnClickListener {

            var detailsIntent:Intent = Intent(activity,ProductDetailsActivity::class.java)
            detailsIntent.putExtra(ProductDetailsActivity.PRODUCT_KEY,product)

            val pairImageView = Pair.create<View,String>(fragment_product_imageView,"productImage")
            val pairBackgroundView = Pair.create<View,String>(fragment_product_background_view,"productImageBackgroundView")
            val pairs:Array<Pair<View, String>> = arrayOf(pairImageView,pairBackgroundView)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as FragmentActivity,*pairs)
            startActivity(detailsIntent,options.toBundle())
        })

        fragment_product_plus.setOnClickListener(View.OnClickListener {
            var count = fragment_product_quantity.text.toString().toInt()
            if(count + 1 > 10){
                Toast.makeText(context,"Max 10 items",Toast.LENGTH_SHORT).show()
            } else {
                fragment_product_quantity.text = (count + 1).toString()
            }
        })

        fragment_product_minus.setOnClickListener(View.OnClickListener {
            var count = fragment_product_quantity.text.toString().toInt()
            if(count - 1 < 1){
                Toast.makeText(context,"Minimum 1 item",Toast.LENGTH_SHORT).show()
            } else {
                fragment_product_quantity.text = (count - 1).toString()
            }
        })

        fragment_product_addtocart.setOnClickListener(View.OnClickListener {
            var count = fragment_product_quantity.text.toString().toInt()
            listener?.onAddItemToCart(product,count)
        })

    }

    // Listener interface
    interface Listener{
        fun onAddItemToCart(product:Product?, count:Int):Unit
    }
}
