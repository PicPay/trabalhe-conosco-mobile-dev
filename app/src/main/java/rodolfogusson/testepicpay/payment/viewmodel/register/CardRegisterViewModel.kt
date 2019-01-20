package rodolfogusson.testepicpay.payment.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import java.util.*

class CardRegisterViewModel(application: Application) : AndroidViewModel(application) {

    val cardNumber = MutableLiveData<String>()
    val cardNumberError = MutableLiveData<String>()

    val cardHolderName = MutableLiveData<String>()
    val cardHolderNameError = MutableLiveData<String>()

    val expiryDate = MutableLiveData<String>()
    val expiryDateError = MutableLiveData<String>()

    val cvv = MutableLiveData<String>()
    val cvvError = MutableLiveData<String>()

    private var timer = Timer()
    private val delay: Long = 1000

    private class Validation(val data: MutableLiveData<String>,
                             val error: MutableLiveData<String>,
                             val validationFunction: (Boolean) -> Boolean) {
        fun validate() {
            if ( &&
        }
    }
    //TODO: FAZER VALIDAÇAO AQUI DENTRO?

    fun validateFieldData(data: MutableLiveData<String>) {

        //check if button should be shown (all fields filled)

        val validation = when (data) {
            cardNumber -> Validation(data, cardNumberError, this::validateCardNumber)
            cardHolderName -> Validation(data, cardHolderNameError, this::validateCardHolderName)
            else -> null
        }

        // Reset error when new characters are typed
        validation?.error?.postValue(null)

        // Delayed validation, as new characters are typed in the fields
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                validation?.validate()
            }
        }, delay)
    }

    private fun getString(id: Int): String? {
        return getApplication<Application>().resources.getString(id)
    }

    private fun validateCardNumber(delayedValidation: Boolean): Boolean {
        val number = cardNumber.value?.replace("\\s".toRegex(), "")
        if (number.isNullOrEmpty()) {
            if (!delayedValidation) {
                cardNumberError.postValue(getString(R.string.error_required_field))
                return false
            }
        } else if (number.length != 16) {
            cardNumberError.postValue(getString(R.string.error_card_number_length))
            return false
        }

        cardNumberError.postValue(null)
        return true
    }

    private fun validateCardHolderName(delayedValidation: Boolean): Boolean {
        if (cardHolderName.value.isNullOrEmpty()) {
            if (!delayedValidation) {
                cardHolderNameError.postValue(getString(R.string.error_required_field))
                return false
            }
        }

        cardHolderNameError.postValue(null)
        return true
    }

    private fun validateExpiryDate(delayedValidation: Boolean) : Boolean {
        if (expiryDate.value.isNullOrEmpty()) {
            if (!delayedValidation) {
                expiryDateError.postValue()
                return false
            }
        }
    }
}