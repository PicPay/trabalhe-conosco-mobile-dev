package rodolfogusson.testepicpay.contacts.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rodolfogusson.testepicpay.contacts.model.user.User
import rodolfogusson.testepicpay.contacts.model.user.UserRepository

class ContactsListViewModel: ViewModel(){
    private val repository = UserRepository()
    var data = MutableLiveData<List<User>>()

    fun getUsers() {
        repository.getData { list, throwable ->

        }
//        println(data)
//        data = repository.getUsers()
//        println(data)

//        ServiceGenerator.sendMoneyService().getUsers().enqueue(object: Callback<List<User>> {
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                println("ERRO: ${t.localizedMessage}")
//            }
//
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                response.body()?.let {
//                    val res = ApiResponse<List<User>>()
//                    res.response = it
//                    data.value = res
//                }
//            }
//
//        })
    }
}