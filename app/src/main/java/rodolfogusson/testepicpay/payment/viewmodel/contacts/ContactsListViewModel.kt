package rodolfogusson.testepicpay.payment.viewmodel.contacts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource

class ContactsListViewModel: ViewModel(){
    private val repository = ContactRepository()
    val contacts: LiveData<Resource<List<Contact>>>

    init {
        contacts = repository.getContacts()
    }
}