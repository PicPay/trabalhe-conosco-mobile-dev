package com.v1pi.picpay_teste.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.widget.ImageView
import java.lang.ref.WeakReference
import java.nio.Buffer


class DownloadImageTask(bmImage: ImageView?) : AsyncTask<String, Unit, Bitmap>() {
    private val bmImage : WeakReference<ImageView>?

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urldisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            val inputS = java.net.URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(inputS)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        return mIcon11
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage?.get()?.let {
            it.setImageDrawable(ImageUtils.createCircularImage(it.resources, result))
        }
    }

    init {
        this.bmImage = WeakReference<ImageView>(bmImage)
    }

}