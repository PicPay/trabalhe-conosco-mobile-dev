package br.com.picpay.picpay.base

import android.arch.lifecycle.ViewModel
import br.com.picpay.picpay.PicpayApplication
import br.com.picpay.picpay.ui.contact.ContactViewModel
import br.com.picpay.picpay.ui.register.RegisterCreditCardViewModel
import br.com.picpay.picpay.ui.transaction.TransactionViewModel

abstract class BaseViewModel: ViewModel() {

    init {
        injector()
    }

    private fun injector() {
        when(this){
            is ContactViewModel -> PicpayApplication.graph.inject(this)
            is RegisterCreditCardViewModel -> PicpayApplication.graph.inject(this)
            is TransactionViewModel -> PicpayApplication.graph.inject(this)
        }
    }

}