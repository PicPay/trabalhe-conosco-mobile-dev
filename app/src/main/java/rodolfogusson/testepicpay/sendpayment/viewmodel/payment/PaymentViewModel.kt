package rodolfogusson.testepicpay.sendpayment.viewmodel.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.core.utils.asExpiryString
import rodolfogusson.testepicpay.core.utils.getString
import rodolfogusson.testepicpay.core.utils.removeWhitespaces
import rodolfogusson.testepicpay.core.utils.toBigDecimalFromCurrency
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.model.payment.PaymentRequest
import rodolfogusson.testepicpay.sendpayment.model.payment.PaymentResponse
import rodolfogusson.testepicpay.sendpayment.model.payment.TransactionRepository


class PaymentViewModel(
    application: Application,
    providedCreditCard: CreditCard,
    providedContact: Contact
) : AndroidViewModel(application) {

    private val creditCard = MutableLiveData<CreditCard>()

    val contact = MutableLiveData<Contact>()

    var cardIdentifier = MediatorLiveData<String>()

    val isValueZero = MediatorLiveData<Boolean>()

    val paymentValue = MutableLiveData<String>()

    // Initial string value for paymentValue
    private val ZERO = "0,00"

    private val transactionRepository = TransactionRepository()

    init {
        contact.value = providedContact

        cardIdentifier.addSource(creditCard) {
            getString(R.string.card_brand_and_initial_number)?.let { string ->
                cardIdentifier.value = String.format(string, it.number.take(4))
            }
        }

        creditCard.value = providedCreditCard

        paymentValue.value = ZERO

        isValueZero.addSource(paymentValue) {
            isValueZero.value = it == ZERO
        }
    }

    fun updateCreditCard(newCreditCard: CreditCard) {
        creditCard.value = newCreditCard
    }

    fun makePayment(): LiveData<Resource<PaymentResponse>>? {
        val value = paymentValue.value
        val card = creditCard.value
        val contact = contact.value
        if (value != null && card != null && contact != null) {
            val paymentRequest = PaymentRequest(
                card.number.removeWhitespaces(),
                card.cvv.toInt(),
                value.toBigDecimalFromCurrency(),
                card.expiryDate.asExpiryString(),
                contact.id
            )
            return transactionRepository.makePayment(paymentRequest)
        }
        return null
    }
}
