package rodolfogusson.testepicpay.payment.viewmodel.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCardRepository

class ContactsListViewModel(application: Application) : AndroidViewModel(application) {
    private val contactRepository = ContactRepository()
    val creditCardRepository = CreditCardRepository.getInstance(application)
    val contacts: LiveData<Resource<List<Contact>>>
    val creditCards: LiveData<List<CreditCard>>

    init {
        contacts = contactRepository.getContacts()
        creditCards = creditCardRepository.getCreditCards()
    }
}