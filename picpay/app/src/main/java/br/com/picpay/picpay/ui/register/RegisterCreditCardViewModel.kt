package br.com.picpay.picpay.ui.register

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.picpay.picpay.base.BaseViewModel
import br.com.picpay.picpay.db.CreditCard
import br.com.picpay.picpay.repository.CreditCardRepository
import br.com.picpay.picpay.utils.Constants.Companion.SAVED_CARD
import br.com.picpay.picpay.utils.Constants.Companion.SHARED_USER
import br.com.picpay.picpay.utils.Validations
import javax.inject.Inject

class RegisterCreditCardViewModel: BaseViewModel() {

    @Inject
    lateinit var creditCardRepository: CreditCardRepository

    private var validationNum = false
    private var validationName = false
    private var validationExp = false
    private var validationCvv = false
    private var control = false
    private lateinit var preferences: SharedPreferences

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()
    val finish: MutableLiveData<Boolean> = MutableLiveData()

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
        if (control) {
            preferences = activity.getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(SAVED_CARD, true)
            editor.apply()
            activity.setResult(RESULT_OK)
        }
        activity.finish()
    }

    fun saveCreditCard(cardNum: String, cardName: String, cardExpiration: String, cardCvv: Int){
        val creditCard = CreditCard()
        creditCard.cardNumber = cardNum
        creditCard.cardholderName = cardName
        creditCard.expiryDate = cardExpiration
        creditCard.cvv = cardCvv
        creditCardRepository.saveCreditCard(creditCard)
        finishSave()
    }

    private fun finishSave() {
        control = true
        finish.value = control
    }

    private fun showButton() {
        if (validationNum && validationName && validationExp && validationCvv) loadingVisibility.value = View.VISIBLE
        else loadingVisibility.value = View.GONE
    }
}