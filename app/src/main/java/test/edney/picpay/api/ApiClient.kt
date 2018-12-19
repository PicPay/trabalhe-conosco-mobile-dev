package test.edney.picpay.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.edney.picpay.BuildConfig
import java.util.concurrent.TimeUnit

class ApiClient {

      private val okHttpClient: OkHttpClient

      init {
            val defaultTimeOut = 120
            okHttpClient = OkHttpClient().newBuilder()
                  .connectTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                  .readTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                  .writeTimeout(defaultTimeOut.toLong(), TimeUnit.SECONDS)
                  .build()
      }

      fun getRequests() = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiRequest::class.java)
}