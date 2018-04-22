package com.v1pi.picpay_teste.Utils

import com.v1pi.picpay_teste.Services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig(url : String = "http://careers.picpay.com/tests/mobdev/"){
    private val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    val userService : UserService get() = retrofit.create(UserService::class.java)
}