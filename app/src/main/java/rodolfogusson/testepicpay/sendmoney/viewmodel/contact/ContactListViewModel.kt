package rodolfogusson.testepicpay.sendmoney.viewmodel.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository
import rodolfogusson.testepicpay.sendmoney.model.payment.Transaction

class ContactListViewModel(
    application: Application,
    transaction: Transaction?
) : AndroidViewModel(application) {

    val contacts: LiveData<Resource<List<Contact>>>

    val lastRegisteredCard: LiveData<CreditCard>

    val transactionCompleted = MutableLiveData<Transaction>()

    private val creditCardRepository = CreditCardRepository.getInstance(application)

    private val contactRepository = ContactRepository()

    init {
        contacts = contactRepository.getContacts()
        lastRegisteredCard = creditCardRepository.getLastRegisteredCreditCard()
        transaction?.let { transactionCompleted.value = it }
    }
}