package rodolfogusson.testepicpay.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rodolfogusson.testepicpay.core.network.services.SendMoneyService

object ServiceProvider {

    private const val baseUrl = "http://careers.picpay.com/tests/mobdev/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun sendMoneyService(): SendMoneyService = retrofit.create(SendMoneyService::class.java)
}
