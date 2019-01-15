package br.com.picpay.picpay

import android.app.Application
import br.com.picpay.picpay.di.component.ApplicationComponent
import br.com.picpay.picpay.di.component.DaggerApplicationComponent
import br.com.picpay.picpay.di.module.NetworkModule

class PicpayApplication: Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule(this))
            .build()

        graph.inject(this)
    }
}