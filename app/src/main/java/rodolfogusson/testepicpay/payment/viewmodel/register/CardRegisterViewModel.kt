package rodolfogusson.testepicpay.payment.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import java.util.*
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediately
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel.ValidationMode.Delayed

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

    private val validations = arrayOf(
        Validation(cardNumber, cardNumberError, this::validateCardNumber),
        Validation(cardHolderName, cardHolderNameError, null),
        Validation(expiryDate, expiryDateError, this::validateExpiryDate),
        Validation(cardNumber, cardNumberError, this::validateCvv)
    )

    private inner class Validation(val data: MutableLiveData<String>,
                                   val error: MutableLiveData<String>,
                                   val hasValidData: (() -> Boolean)?) {

        fun validate(mode: ValidationMode) : Boolean {
            if (data.value.isNullOrEmpty()) {
                if (mode == Immediately) {
                    // Field is required at this moment and can't be null or empty! Show an error
                    cardNumberError.postValue(getString(R.string.error_required_field))
                    return false
                }
            } else if (hasValidData != null && !hasValidData.invoke()) {
                return false
            }
            cardNumberError.postValue(null)
            return true
        }
    }

    enum class ValidationMode {
        Immediately, Delayed
    }

    fun onDataChanged(data: MutableLiveData<String>) {

        //check if button should be shown (all fields filled)

        val validation = validations.first { it.data == data }

        // Reset error when new characters are typed
        validation.error.postValue(null)

        // Delayed validation, as new characters are typed in the fields
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                validation.validate(Delayed)
            }
        }, delay)
    }

    private fun getString(id: Int): String? {
        return getApplication<Application>().resources.getString(id)
    }

    private fun validateCardNumber(): Boolean {
        val number = cardNumber.value?.replace("\\s".toRegex(), "")
        if (number?.length != 16) {
            cardNumberError.postValue(getString(R.string.error_card_number_length))
            return false
        }

        cardNumberError.postValue(null)
        return true
    }


    private fun validateExpiryDate() : Boolean {
        val formatter: DateFormatter
        val date = Date()
    }

    private fun validateCvv() : Boolean {

    }
}