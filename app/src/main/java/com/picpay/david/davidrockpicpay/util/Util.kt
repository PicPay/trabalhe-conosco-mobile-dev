package com.picpay.david.davidrockpicpay.util

import android.content.Context
import android.net.ConnectivityManager

object Util{
    fun isOnline(cont: Context): Boolean {
        val conmag = cont.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conmag.activeNetworkInfo
        //Verifica o WIFI
        return if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected) {
            true
        } else conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected //Verifica o 3G

    }
}