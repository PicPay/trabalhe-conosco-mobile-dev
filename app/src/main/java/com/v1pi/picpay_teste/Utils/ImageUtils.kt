package com.v1pi.picpay_teste.Utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory

class ImageUtils {
    companion object {
        fun createCircularImage(res : Resources, bitmap : Bitmap) : RoundedBitmapDrawable{
            val drawable :RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(res, bitmap)
            drawable.cornerRadius = Math.max(bitmap.width, bitmap.height) / 1.25f
            drawable.isCircular = true
            return drawable
        }
    }
}