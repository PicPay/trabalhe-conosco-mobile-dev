package test.edney.picpay.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    private lateinit var api: ApiRequest
    private lateinit var okHttpClient: OkHttpClient

    fun ApiClient() {
        val defaultTimeOut = 180
        okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
            .build()

        configureApiRequest()
    }


    private fun configureApiRequest() {
        api = Retrofit.Builder()
            .baseUrl(EndPoint.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRequest::class.java)
    }

    fun getRequest(): ApiRequest {
        return api
    }
}