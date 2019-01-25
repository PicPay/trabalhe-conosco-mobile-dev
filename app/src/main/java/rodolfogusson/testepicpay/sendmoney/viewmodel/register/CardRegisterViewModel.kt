package rodolfogusson.testepicpay.sendmoney.viewmodel.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.utils.asExpiryString
import rodolfogusson.testepicpay.core.utils.getString
import rodolfogusson.testepicpay.core.utils.removeWhitespaces
import rodolfogusson.testepicpay.core.utils.toExpiryDate
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Delayed
import rodolfogusson.testepicpay.sendmoney.viewmodel.register.CardRegisterViewModel.ValidationMode.Immediate
import java.time.LocalDate
import java.util.*

class CardRegisterViewModel(
    application: Application,
    private val providedCreditCard: CreditCard?
    ) : AndroidViewModel(application) {

    val cardNumberField = MutableLiveData<String>()
    val cardNumberError = MutableLiveData<String>()

    val cardholderNameField = MutableLiveData<String>()

    val expiryDateField = MutableLiveData<String>()
    val expiryDateError = MutableLiveData<String>()
    private var expiryDate: LocalDate? = null

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
        providedCreditCard?.let {
            fillFieldsWith(providedCreditCard)
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
            expiryDateField.value = expiryDate.asExpiryString()
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

                /*  If no providedCreditCard was passed in the constructor, a new one will be inserted.
                 *  Otherwise, we'll use the id of the passed card to replace it in the database.
                 */
                val creditCardToBeSaved = CreditCard(
                    id = providedCreditCard?.id,
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

    // Validation functions:

    private fun cardNumberIsValid(validation: Validation): Boolean {
        validation.field.value?.let { string ->
            val numberString = string.removeWhitespaces()
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
            val date = string.toExpiryDate()
            expiryDate = date
            if (date == null || date < LocalDate.now()) {
                validation.error?.postValue(getString(R.string.error_expiry_date_not_valid))
                return false
            }
        }
        validation.error?.postValue(null)
        return true
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