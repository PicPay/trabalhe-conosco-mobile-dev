package br.com.picpay.picpay.base

import android.arch.lifecycle.ViewModel
import br.com.picpay.picpay.PicpayApplication
import br.com.picpay.picpay.ui.contact.ContactViewModel

abstract class BaseViewModel: ViewModel() {

    init {
        injector()
    }

    private fun injector() {
        when(this){
            is ContactViewModel -> PicpayApplication.graph.inject(this)
        }
    }

}