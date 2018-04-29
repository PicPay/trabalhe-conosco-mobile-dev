package com.v1pi.picpay_teste.Utils

import android.os.AsyncTask
import io.reactivex.functions.Consumer
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


internal class InternetCheck(private val mConsumer: Consumer<Boolean>? = null) : AsyncTask<Void, Void, Boolean>() {

    init {
        execute()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            return true
        } catch (e: IOException) {
            return false
        }

    }

    override fun onPostExecute(internet: Boolean?) {
        mConsumer?.accept(internet ?: false)
    }
}