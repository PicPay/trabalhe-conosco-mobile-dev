package br.com.kassianoresende.picpay.application

import android.app.Application

class PicPayApp: Application() {

    companion object {
        lateinit var appContext: Application

    }

    override fun onCreate() {
        appContext = this
        super.onCreate()
    }

}