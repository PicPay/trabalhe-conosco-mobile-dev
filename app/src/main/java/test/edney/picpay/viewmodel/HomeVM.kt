package test.edney.picpay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.edney.picpay.api.ApiClient
import test.edney.picpay.model.UserModel

class HomeVM(application: Application) : AndroidViewModel(application) {

    private val api = ApiClient().getRequests()
    val userResponse = MutableLiveData<List<UserModel>>()

    fun requestUsers(){
        api.getUsers().enqueue(object : Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                userResponse.value = null
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                userResponse.value = response.body()
            }
        })
    }
}