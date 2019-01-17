package br.com.picpay.picpay.ui.priming

import android.content.Context
import android.content.Intent
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.ui.register.RegisterCreditCardActivity

class PrimingViewModel: BaseViewModel() {

    fun setActivityRegister(context: Context){
        val intent = Intent(context, RegisterCreditCardActivity::class.java)
        context.startActivity(intent)
    }
}