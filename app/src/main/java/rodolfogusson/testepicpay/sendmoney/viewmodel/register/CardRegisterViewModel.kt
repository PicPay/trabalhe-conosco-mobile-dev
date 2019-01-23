package rodolfogusson.testepicpay.sendmoney.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository
import java.util.*
import java.lang.Exception
import java.time.LocalDate
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediate
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Delayed

class CardRegisterViewModel(application: Application) : AndroidViewModel(application) {

    val cardNumber = MutableLiveData<String>()
    val cardNumberError = MutableLiveData<String>()

    val cardHolderName = MutableLiveData<String>()

    val expiryDate = MutableLiveData<String>()
    private var expiryLocalDate: LocalDate? = null
    val expiryDateError = MutableLiveData<String>()

    val cvv = MutableLiveData<String>()
    val cvvError = MutableLiveData<String>()

    val saveButtonVisible = MutableLiveData<Boolean>().apply { value = false }

    val registeredCreditCard = MutableLiveData<CreditCard>()

    private val creditCardRepository = CreditCardRepository.getInstance(application)

    private val validations = arrayOf(
        Validation(cardNumber, cardNumberError, this::cardNumberIsValid),
        Validation(cardHolderName, null, null),
        Validation(expiryDate, expiryDateError, this::expiryDateIsValid),
        Validation(cvv, cvvError, this::cvvIsValid)
    )

    /**
     * Objects of this class represent the validation of one field on the screen.
     */
    private inner class Validation(
        val field: MutableLiveData<String>,
        val error: MutableLiveData<String>?,
        val specificValidation: ((Validation) -> Boolean)?
    ) {

        private var timer = Timer()
        private val delay: Long = 1000
        var passed = false

        fun validate(mode: ValidationMode) {
            timer.cancel()
            if (field.value.isNullOrEmpty()) {
                passed = false
                error?.postValue(null)
                return
            } else {
                executeSpecificValidation(mode)
            }
        }

        private fun executeSpecificValidation(mode: ValidationMode) {
            if (specificValidation == null) {
                // No need for any specific validation in this case
                passed = true
                return
            }

            if (mode == Immediate) {
                // Immediate validation
                passed = specificValidation.invoke(this)
            } else {
                // Delayed validation
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        passed = specificValidation.invoke(this@Validation)
                    }
                }, delay)
            }
        }
    }

    private enum class ValidationMode {
        Immediate, Delayed
    }

    fun validate(field: MutableLiveData<String>) {
        // Get the validation object corresponding to the altered field
        validations.firstOrNull { it.field == field }?.let { validation ->

            // Reset error when new characters are typed
            validation.error?.postValue(null)

            // Show save button if all fields are filled
            saveButtonVisible.postValue(allFieldsAreFilled())

            // Validate the newly entered data
            validation.validate(Delayed)
        }
    }

    fun saveCreditCard()  {
        if (allFieldsAreValid()) {
            val numberString = cardNumber.value
            val nameString = cardHolderName.value
            val date = expiryLocalDate
            val cvvString = cvv.value

            if (numberString != null &&
                nameString != null &&
                date != null &&
                cvvString != null) {

                val creditCard = CreditCard(
                    cardNumber = numberString,
                    cardName = nameString,
                    expiryDate = date,
                    cvv = cvvString
                )

                creditCardRepository.insert(creditCard)
                registeredCreditCard.postValue(creditCard)
            }
        }
    }

    private fun allFieldsAreValid(): Boolean {
        for (validation in validations) {
            validation.validate(Immediate)
            if (!validation.passed) return false
        }
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
        validation.field.value?.let { string ->
            val numberString = string.replace("\\s".toRegex(), "")
            if (numberString.length != 16) {
                validation.error?.postValue(getString(R.string.error_card_number_length))
                return false
            }
        }
        validation.error?.postValue(null)
        return true
    }

    private fun expiryDateIsValid(validation: Validation): Boolean {
        validation.field.value?.let { string ->
            val date = stringToLocalDate(string)
            expiryLocalDate = date
            if (date == null || date < LocalDate.now()) {
                validation.error?.postValue(getString(R.string.error_expiry_date_not_valid))
                return false
            }
        }
        validation.error?.postValue(null)
        return true
    }

    private fun stringToLocalDate(string: String): LocalDate? {
        if (string.length == 5) {
            val parts = string.split("/")

            val monthString = parts[0]

            val finalDigitsOfYear = parts[1]
            val currentDate = LocalDate.now()
            val currentYearString = currentDate.toString().split("-").first()
            val yearString = currentYearString.take(currentYearString.length - 2) + finalDigitsOfYear

            return try {
                val month = monthString.toInt()
                val year = yearString.toInt()
                val initialDate = LocalDate.of(year, month, 1)
                // Expiry dates are relative to the last day of its month
                initialDate.withDayOfMonth(initialDate.lengthOfMonth())
            } catch (e: Exception) {
                // Entered date has wrong format or is before today
                null
            }

        } else {
            // Entered date has wrong format
            return null
        }
    }

    private fun cvvIsValid(validation: Validation): Boolean {
        validation.field.value?.let { string ->
            if (string.length != 3) {
                validation.error?.postValue(getString(R.string.error_cvv_length))
                return false
            }
        }
        validation.error?.postValue(null)
        return true
    }
}