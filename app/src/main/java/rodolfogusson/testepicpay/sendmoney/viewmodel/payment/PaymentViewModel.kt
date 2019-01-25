package rodolfogusson.testepicpay.sendmoney.viewmodel.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.R
import rodolfogusson.testepicpay.core.utils.getString
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard


class PaymentViewModel(
    application: Application,
    providedCreditCard: CreditCard,
    providedContact: Contact
) : AndroidViewModel(application) {

    val contact = MutableLiveData<Contact>()
    val creditCard = MutableLiveData<CreditCard>()
    var cardIdentifierString: String? = null

    private val ZERO = "0,00"
    val paymentValue = MutableLiveData<String>()
    var isValueZero = MediatorLiveData<Boolean>()

    init {
        creditCard.value = providedCreditCard
        contact.value = providedContact
        getString(R.string.card_brand_and_initial_number)?.let {
            cardIdentifierString = String.format(it, providedCreditCard.number.take(4))
        }
        paymentValue.value = ZERO
        isValueZero.addSource(paymentValue) {
            isValueZero.value = it == ZERO
        }
    }
}