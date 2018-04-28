package com.v1pi.picpay_teste.Utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.v1pi.picpay_teste.Services.TransactionService
import com.v1pi.picpay_teste.Services.UserService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig(url : String = "http://careers.picpay.com/tests/mobdev/", gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()){
    private val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    val userService : UserService get() = retrofit.create(UserService::class.java)

    val transactionService : TransactionService get() = retrofit.create(TransactionService::class.java)
}