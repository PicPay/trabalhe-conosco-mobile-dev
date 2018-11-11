package test.edney.picpay.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    var request: ApiRequest

    init {
        val defaultTimeOut = 180
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .build()

        request = Retrofit.Builder()
            .baseUrl(EndPoint.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRequest::class.java)
    }
}