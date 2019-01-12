package rodolfogusson.testepicpay.contacts.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rodolfogusson.testepicpay.contacts.model.contact.Contact
import rodolfogusson.testepicpay.contacts.model.contact.ContactRepository
import rodolfogusson.testepicpay.core.data.Resource

class ContactsListViewModel: ViewModel(){
    private val repository = ContactRepository()
    var users = MutableLiveData<Resource<List<Contact>>>()

    fun getUsers() {
        repository.getData {
            users.value = it
        }
    }
}