package br.com.vdsantana.picpaytest.main.di

import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.di.component.AppComponent
import br.com.vdsantana.picpaytest.main.MainActivity
import dagger.Component

/**
 * Created by vd_sa on 30/03/2018.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)
}