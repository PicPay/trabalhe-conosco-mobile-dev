package com.picpay.david.davidrockpicpay.api

import android.provider.SyncStateContract
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class PicPayAPI(client: OkHttpClient) {

    private val retrofit: Retrofit
    private val api: IPicPayAPI

    init  {
        retrofit = Retrofit.Builder()
                .baseUrl("http://careers.picpay.com/tests/mobdev/")
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retrofit.create(IPicPayAPI::class.java)

    }

    fun getInstance(): IPicPayAPI {
        return api
    }
}