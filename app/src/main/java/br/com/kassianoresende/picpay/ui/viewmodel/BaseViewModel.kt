package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.kassianoresende.picpay.di.component.DaggerViewModelInjector
import br.com.kassianoresende.picpay.di.component.ViewModelInjector

abstract class BaseViewModel:ViewModel() {

    val component: ViewModelInjector = DaggerViewModelInjector.create()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> component.inject(this)
        }
    }

}