package com.sdl.moments.util

import android.graphics.drawable.Drawable
import android.widget.TextView

object ViewUtil {

    @JvmStatic
    fun modifyTextViewDrawable(v: TextView, drawable: Drawable, index: Int) {
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        //index 0:左 1：上 2：右 3：下
        when (index) {
            0 -> v.setCompoundDrawables(drawable, null, null, null)
            1 -> v.setCompoundDrawables(null, drawable, null, null)
            2 -> v.setCompoundDrawables(null, null, drawable, null)
            else -> v.setCompoundDrawables(null, null, null, drawable)
        }
    }
}