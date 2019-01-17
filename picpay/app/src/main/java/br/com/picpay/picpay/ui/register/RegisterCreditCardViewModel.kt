package br.com.picpay.picpay.ui.register

import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.model.CreditCard
import br.com.picpay.picpay.ui.contact.ContactActivity
import br.com.picpay.picpay.utils.Validations

class RegisterCreditCardViewModel: BaseViewModel() {
    private var validationNum = false
    private var validationName = false
    private var validationExp = false
    private var validationCvv = false

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    fun validationExp(cardExpiration: String){
        if (cardExpiration.length == 5) {
            validationExp = Validations.validateCardExpiryDate(cardExpiration)
            error.value = validationExp
        } else validationExp = false
        showButton()
    }

    fun validationNum(cardNum: String){
        validationNum = cardNum.length == 16
        showButton()
    }

    fun validationName(cardName: String){
        validationName = cardName.isNotEmpty()
        showButton()
    }

    fun validationCvv(cardCvv: String){
        validationCvv = cardCvv.length == 3
        showButton()
    }

    fun setContactActivity(activity: AppCompatActivity) {
        val intent = Intent(activity, ContactActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        activity.finish()
    }

    fun saveCreditCard(creditCard: CreditCard){
        //Persist on a database
    }

    private fun showButton() {
        if (validationNum && validationName && validationExp && validationCvv) loadingVisibility.value = View.VISIBLE
        else loadingVisibility.value = View.GONE
    }
}