package rodolfogusson.testepicpay.sendmoney.viewmodel.payment

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
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.payment.PaymentRequest
import rodolfogusson.testepicpay.sendmoney.model.payment.PaymentResponse
import rodolfogusson.testepicpay.sendmoney.model.payment.TransactionRepository


class PaymentViewModel(
    application: Application,
    private val providedCreditCard: CreditCard,
    private val providedContact: Contact
) : AndroidViewModel(application) {

    val contact = MutableLiveData<Contact>()

    val isValueZero = MediatorLiveData<Boolean>()

    val paymentValue = MutableLiveData<String>()

    // Initial string value for paymentValue
    private val ZERO = "0,00"

    var cardIdentifierString: String? = null

    private val transactionRepository = TransactionRepository()

    init {
        contact.value = providedContact
        getString(R.string.card_brand_and_initial_number)?.let {
            cardIdentifierString = String.format(it, providedCreditCard.number.take(4))
        }
        paymentValue.value = ZERO
        isValueZero.addSource(paymentValue) {
            isValueZero.value = it == ZERO
        }
    }

    fun makePayment(): LiveData<Resource<PaymentResponse>> {
        paymentValue.value?.let {
            val paymentRequest = PaymentRequest(
                providedCreditCard.number.removeWhitespaces(),
                providedCreditCard.cvv.toInt(),
                it.replace("[,]".toRegex(), ".").toBigDecimal(),
                providedCreditCard.expiryDate.asExpiryString(),
                providedContact.id
            )
            return transactionRepository.makePayment(paymentRequest)
        }
    }
}