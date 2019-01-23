package rodolfogusson.testepicpay.sendmoney.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository
import java.util.*
import java.lang.Exception
import java.time.LocalDate
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediate
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Delayed
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CardRegisterViewModel(
    application: Application,
    private val creditCard: CreditCard?
    ) : AndroidViewModel(application) {

    val cardNumberField = MutableLiveData<String>()
    val cardNumberError = MutableLiveData<String>()

    val cardholderNameField = MutableLiveData<String>()

    val expiryDateField = MutableLiveData<String>()
    val expiryDateError = MutableLiveData<String>()
    private var expiryDate: LocalDate? = null
    private val formatter = DateTimeFormatter.ofPattern("MM/yy")


    val cvvField = MutableLiveData<String>()
    val cvvError = MutableLiveData<String>()

    private val validations = arrayOf(
        Validation(cardNumberField, cardNumberError, this::cardNumberIsValid),
        Validation(cardholderNameField, null, null),
        Validation(expiryDateField, expiryDateError, this::expiryDateIsValid),
        Validation(cvvField, cvvError, this::cvvIsValid)
    )

    val saveButtonVisible = MutableLiveData<Boolean>().apply { value = false }

    private val creditCardRepository = CreditCardRepository.getInstance(application)
    private val lastRegisteredCreditCard = creditCardRepository.getLastRegisteredCreditCard()
    val savedCreditCard = MediatorLiveData<CreditCard>()
    private var creditCardWasSaved = false

    init {
        creditCard?.let {
            fillFieldsWith(creditCard)
        }
        savedCreditCard.addSource(lastRegisteredCreditCard) {
            if (creditCardWasSaved) {
                savedCreditCard.value = it
            }
        }
    }

    private fun fillFieldsWith(creditCard: CreditCard) {
        with(creditCard) {
            cardNumberField.value = number
            cardholderNameField.value = name
            expiryDateField.value = expiryDate.format(formatter)
            cvvField.value = cvv
        }
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

    fun saveCreditCard() {
        if (allFieldsAreValid()) {
            val numberString = cardNumberField.value
            val nameString = cardholderNameField.value
            val date = expiryDate
            val cvvString = cvvField.value

            if (numberString != null &&
                nameString != null &&
                date != null &&
                cvvString != null) {

                val creditCardToBeSaved = CreditCard(
                    id = creditCard?.id,
                    number = numberString,
                    name = nameString,
                    expiryDate = date,
                    cvv = cvvString
                )

                creditCardWasSaved = true
                creditCardRepository.insert(creditCardToBeSaved)
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
        return !cardNumberField.value.isNullOrEmpty() &&
                !cardholderNameField.value.isNullOrEmpty() &&
                !expiryDateField.value.isNullOrEmpty() &&
                !cvvField.value.isNullOrEmpty()
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
            val date = localDateFrom(string)
            expiryDate = date
            if (date == null || date < LocalDate.now()) {
                validation.error?.postValue(getString(R.string.error_expiry_date_not_valid))
                return false
            }
        }
        validation.error?.postValue(null)
        return true
    }

    private fun localDateFrom(string: String): LocalDate? {
        return if (string.length == 5) {
            try {
                val yearMonth = YearMonth.parse(string, formatter)
                // Expiry dates are relative to the last day of its month
                yearMonth.atDay(yearMonth.lengthOfMonth())
            } catch (e: Exception) {
                // Entered date has wrong format
                null
            }
        } else {
            // Entered date has wrong format
            null
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
}