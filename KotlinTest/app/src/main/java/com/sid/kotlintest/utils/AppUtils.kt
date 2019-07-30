package com.sid.kotlintest.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

class AppUtils{
    companion object {
        var themeColors:IntArray = intArrayOf(Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE,Color.WHITE)

        fun applyGradient(view:View,color:String){
            var colorCode = Color.parseColor(color)
            themeColors[0] = colorCode

            var drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, themeColors)
            val sdk = android.os.Build.VERSION.SDK_INT
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(drawable)
            } else {
                view.background = drawable
            }
        }
    }
}