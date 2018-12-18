package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.kassianoresende.picpay.application.PicPayApp
import br.com.kassianoresende.picpay.di.component.DaggerViewModelInjector
import br.com.kassianoresende.picpay.di.component.ViewModelInjector
import br.com.kassianoresende.picpay.di.module.DatabaseModule

abstract class BaseViewModel:ViewModel() {

    val component: ViewModelInjector = DaggerViewModelInjector
        .builder().databaseModule(DatabaseModule(PicPayApp.appContext)).build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> component.inject(this)
            is NewCardViewModel-> component.inject(this)
            is CardListViewModel-> component.inject(this)
            is CheckoutViewModel -> component.inject(this)
        }
    }

}