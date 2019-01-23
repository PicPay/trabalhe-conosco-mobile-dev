package rodolfogusson.testepicpay.sendmoney.viewmodel.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import rodolfogusson.testepicpay.sendmoney.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.network.Resource
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendmoney.model.creditcard.CreditCardRepository

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val creditCardRepository = CreditCardRepository.getInstance(application)
    private val contactRepository = ContactRepository()

    val contacts: LiveData<Resource<List<Contact>>>
    val lastRegisteredCard: LiveData<CreditCard>

    init {
        contacts = contactRepository.getContacts()
        lastRegisteredCard = creditCardRepository.lastRegisteredCreditCard()
    }
}