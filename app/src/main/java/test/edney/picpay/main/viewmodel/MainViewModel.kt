package test.edney.picpay.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.edney.picpay.api.ApiClient
import test.edney.picpay.api.model.UserModel

class MainViewModel : ViewModel() {

    private val api = ApiClient().request
    val users = MutableLiveData<List<UserModel>>()

    fun getUsers(){
        api.getUsers().enqueue(object : Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                users.value = null
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                users.value = response.body()
            }
        })
    }
}