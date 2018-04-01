package br.com.vdsantana.picpaytest

import android.app.Application
import br.com.vdsantana.picpaytest.di.component.AppComponent
import br.com.vdsantana.picpaytest.di.component.DaggerAppComponent
import br.com.vdsantana.picpaytest.di.modules.AppModule

/**
 * Created by vd_sa on 30/03/2018.
 */
class AppApplication : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}