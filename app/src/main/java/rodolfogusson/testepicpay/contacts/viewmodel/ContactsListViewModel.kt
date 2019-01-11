package rodolfogusson.testepicpay.contacts.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rodolfogusson.testepicpay.contacts.model.user.User
import rodolfogusson.testepicpay.contacts.model.user.UserRepository
import rodolfogusson.testepicpay.core.data.Resource

class ContactsListViewModel: ViewModel(){
    private val repository = UserRepository()
    var users = MutableLiveData<Resource<List<User>>>()

    fun getUsers() {
        repository.getData {
            users.value = it
        }
    }
}