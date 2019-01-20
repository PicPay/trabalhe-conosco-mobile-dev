package com.jvtnascimento.picpay.application

import android.app.Application
import com.jvtnascimento.picpay.dagger.AppComponent
import com.jvtnascimento.picpay.dagger.DaggerAppComponent
import com.jvtnascimento.picpay.dagger.MainPresenterModule

class BaseApplication: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        this.configureDaggerComponent()
    }

    private fun configureDaggerComponent() {
        this.component = DaggerAppComponent.builder()
            .mainPresenterModule(MainPresenterModule())
            .build()
    }
}