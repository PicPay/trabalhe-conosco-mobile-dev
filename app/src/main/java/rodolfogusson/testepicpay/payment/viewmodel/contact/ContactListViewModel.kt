package rodolfogusson.testepicpay.payment.viewmodel.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCardRepository

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val creditCardRepository = CreditCardRepository.getInstance(application)
    private val contactRepository = ContactRepository()

    val contacts: LiveData<Resource<List<Contact>>>
    private val creditCards: LiveData<List<CreditCard>>
    val registeredCard: LiveData<CreditCard>

    init {
        contacts = contactRepository.getContacts()
        creditCards = creditCardRepository.getCreditCards()
        registeredCard = Transformations.map(creditCards) {
            if (it.isNotEmpty()) it[0] else null
        }
    }
}