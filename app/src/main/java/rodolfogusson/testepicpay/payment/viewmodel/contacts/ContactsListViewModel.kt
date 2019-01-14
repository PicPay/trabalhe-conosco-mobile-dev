package rodolfogusson.testepicpay.payment.viewmodel.contacts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rodolfogusson.testepicpay.payment.model.contact.Contact
import rodolfogusson.testepicpay.payment.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource

class ContactsListViewModel: ViewModel(){
    private val repository = ContactRepository()
    var users = MutableLiveData<Resource<List<Contact>>>()

    fun getContacts() {
        repository.getData {
            users.value = it
        }
    }
}