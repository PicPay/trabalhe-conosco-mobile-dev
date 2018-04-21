package com.v1pi.picpay_teste.Utils

import com.v1pi.picpay_teste.Domains.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    fun requestUsers(success : (List<User>) -> Unit) {
        RequestState.REQUEST_USER = RequestState.STATES.PROGRESS

        val call = RetrofitConfig().userService.Users()
        call.enqueue(object: Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>?, response: Response<List<User>?>?) {
                response?.body()?.let {
                    success(it)
                }
                RequestState.REQUEST_USER = RequestState.STATES.READY
            }

            override fun onFailure(call: Call<List<User>?>?, t: Throwable?) {
                RequestState.REQUEST_USER = RequestState.STATES.READY
            }
        })
    }
}