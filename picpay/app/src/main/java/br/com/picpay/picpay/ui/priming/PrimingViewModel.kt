package br.com.picpay.picpay.ui.priming

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.ui.register.RegisterCreditCardActivity
import br.com.picpay.picpay.utils.Constants.Companion.SAVE_CARD

class PrimingViewModel: BaseViewModel() {

    fun setActivityRegister(activity: AppCompatActivity){
        val intent = Intent(activity, RegisterCreditCardActivity::class.java)
        activity.startActivityForResult(intent, SAVE_CARD)
    }

    fun finishActivity(activity: AppCompatActivity, success: Boolean){
        if (success) activity.setResult(RESULT_OK)
        activity.finish()
    }
}