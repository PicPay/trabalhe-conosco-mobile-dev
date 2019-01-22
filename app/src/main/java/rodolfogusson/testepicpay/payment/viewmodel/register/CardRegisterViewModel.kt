package rodolfogusson.testepicpay.payment.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import java.util.*
import java.lang.Exception
import java.time.DateTimeException
import java.time.LocalDate
import rodolfogusson.testepicpay.payment.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediate
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

    val saveButtonVisible = MutableLiveData<Boolean>().apply { value = false }

    private val validations = arrayOf(
        Validation(cardNumber, cardNumberError, this::cardNumberIsValid),
        Validation(cardHolderName, cardHolderNameError, null),
        Validation(expiryDate, expiryDateError, this::expiryDateIsValid),
        Validation(cvv, cvvError, this::cvvIsValid)
    )

    /**
     * Objects of this class represent the validation of one field on the screen.
     */
    private inner class Validation(
        val data: MutableLiveData<String>,
        val error: MutableLiveData<String>,
        val hasValidData: ((Validation) -> Boolean)?
    ) {

        private var timer = Timer()
        private val delay: Long = 1000
        var passed = false

        fun validate(mode: ValidationMode) {
            timer.cancel()
            if (data.value.isNullOrEmpty()) {
                passed = false
                error.postValue(null)
                return
            } else {
                if (hasValidData == null) {
                    passed = true
                    return
                }
                if (mode == Immediate) {
                    // Immediate validation
                    passed = hasValidData.invoke(this)
                } else {
                    // Delayed validation
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            passed = hasValidData.invoke(this@Validation)
                        }
                    }, delay)
                }
            }
        }
    }

    private enum class ValidationMode {
        Immediate, Delayed
    }

    fun onFieldDataChanged(data: MutableLiveData<String>) {
        // Get the validation object corresponding to the altered field
        validations.firstOrNull { it.data == data }?.let { validation ->
            // Reset error when new characters are typed
            validation.error.postValue(null)
            // Show save button if all fields are filled
            saveButtonVisible.postValue(allFieldsAreFilled())
            // Validate the newly entered data
            validation.validate(Delayed)
        }
    }

    fun validateAllFields(): Boolean {
        for (validation in validations) {
            validation.validate(Immediate)
            if (!validation.passed) return false
        }
        //TODO: save data and go to next screen
        return true
    }

    private fun allFieldsAreFilled(): Boolean {
        return !cardNumber.value.isNullOrEmpty() &&
                !cardHolderName.value.isNullOrEmpty() &&
                !expiryDate.value.isNullOrEmpty() &&
                !cvv.value.isNullOrEmpty()
    }

    private fun getString(id: Int): String? {
        return getApplication<Application>().resources.getString(id)
    }

    /**
     * Validation functions:
     */

    private fun cardNumberIsValid(validation: Validation): Boolean {
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

    private fun expiryDateIsValid(validation: Validation): Boolean {
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
                    val initialDate = LocalDate.of(year, month, 1)
                    // Expiry dates are relative to the last day of its month
                    val expiry = initialDate.withDayOfMonth(initialDate.lengthOfMonth())
                    if (expiry < currentDate) throw Exception()
                } catch (e: Exception) {
                    // Entered date has wrong format or is before today
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

    private fun cvvIsValid(validation: Validation): Boolean {
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