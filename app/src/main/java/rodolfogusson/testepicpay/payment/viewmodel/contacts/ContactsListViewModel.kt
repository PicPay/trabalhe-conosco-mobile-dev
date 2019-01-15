package rodolfogusson.testepicpay.payment.viewmodel.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource

class ContactsListViewModel: ViewModel(){
    private val contactRepository = ContactRepository()
    val contacts: LiveData<Resource<List<Contact>>>

    init {
        contacts = contactRepository.getContacts()
    }
}