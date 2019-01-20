package com.jvtnascimento.picpay.dagger

import com.jvtnascimento.picpay.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPresenterModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}