package rodolfogusson.testepicpay.payment.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import java.util.*
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediately
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel.ValidationMode.Delayed
import java.lang.Exception
import java.time.DateTimeException
import java.time.LocalDate

class CardRegisterViewModel(application: Application) : AndroidViewModel(application) {

    val cardNumber = MutableLiveData<String>()
    val cardNumberError = MutableLiveData<String>()

    val cardHolderName = MutableLiveData<String>()
    val cardHolderNameError = MutableLiveData<String>()

    val expiryDate = MutableLiveData<String>()
    val expiryDateError = MutableLiveData<String>()

    val cvv = MutableLiveData<String>()
    val cvvError = MutableLiveData<String>()

    private val validations = arrayOf(
        Validation(cardNumber, cardNumberError, this::isCardNumberValid),
        Validation(cardHolderName, cardHolderNameError, null),
        Validation(expiryDate, expiryDateError, this::isExpiryDateValid),
        Validation(cvv, cvvError, this::validateCvv)
    )

    private inner class Validation(val data: MutableLiveData<String>,
                                   val error: MutableLiveData<String>,
                                   val hasValidData: ((Validation) -> Boolean)?) {

        var timer = Timer()
        val delay: Long = 1000

        fun validate(mode: ValidationMode) : Boolean {
            if (data.value.isNullOrEmpty()) {
                if (mode == Immediately) {
                    // Field is required at this moment and can't be null or empty! Show an error
                    error.postValue(getString(R.string.error_required_field))
                    return false
                }
            } else if (hasValidData != null && !hasValidData.invoke(this)) {
                return false
            }
            error.postValue(null)
            return true
        }
    }

    enum class ValidationMode {
        Immediately, Delayed
    }

    fun onDataChanged(data: MutableLiveData<String>) {

        //check if button should be shown (all fields filled)

        validations.firstOrNull { it.data == data }?.let { validation ->
            // Reset error when new characters are typed
            validation.error.postValue(null)

            // Delayed validation, as new characters are typed in the fields
            validation.timer.cancel()
            validation.timer = Timer()
            validation.timer.schedule(object : TimerTask() {
                override fun run() {
                    validation.validate(Delayed)
                }
            }, validation.delay)
        }
    }

    private fun getString(id: Int): String? {
        return getApplication<Application>().resources.getString(id)
    }

    private fun isCardNumberValid(validation: Validation): Boolean {
        validation.data.value?.let { string ->
            val numberString = string.replace("\\s".toRegex(), "")
            if (numberString.length != 16) {
                validation.error.postValue(getString(R.string.error_card_number_length))
                return false
            }
        }
        validation.error.postValue(null)
        return true
    }


    private fun isExpiryDateValid(validation: Validation) : Boolean {
        validation.data.value?.let { string ->
            if (string.length == 5) {
                val parts = string.split("/")

                val monthString = parts[0]

                val finalDigitsOfYear = parts[1]
                val currentDate = LocalDate.now()
                val currentYearString = currentDate.toString().split("-").first()
                val yearString = currentYearString.take(currentYearString.length - 2) + finalDigitsOfYear

                try {
                    val month = monthString.toInt()
                    val year = yearString.toInt()
                    val enteredDate = LocalDate.of(year, month, 1)
                    if (enteredDate < currentDate) throw DateTimeException("Data passada")
                } catch (e: Exception) {
                    validation.error.postValue(getString(R.string.error_expiry_date_not_valid))
                    return false
                }
            } else {
                validation.error.postValue(getString(R.string.error_expiry_date_not_valid))
                return false
            }
        }
        validation.error.postValue(null)
        return true
    }

    private fun validateCvv(validation: Validation) : Boolean {
        validation.data.value?.let { string ->
            if (string.length != 3) {
                validation.error.postValue(getString(R.string.error_cvv_length))
                return false
            }
        }
        validation.error.postValue(null)
        return true
    }
}