package com.v1pi.picpay_teste.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.v1pi.picpay_teste.R
import java.lang.ref.WeakReference
import java.util.function.Consumer


class DownloadImageTask(bmImage: ImageView?) : AsyncTask<String, Unit, Bitmap?>() {
    private val bmImage : WeakReference<ImageView>?

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urldisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            val inputS = java.net.URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(inputS)
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }
        return mIcon11

    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage?.get()?.let {
            result?.let { bit : Bitmap ->
                it.setImageDrawable(ImageUtils.createCircularImage(it.resources, bit))
            }
        }
    }

    init {
        this.bmImage = WeakReference<ImageView>(bmImage)
    }

}