package rodolfogusson.testepicpay.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rodolfogusson.testepicpay.core.network.services.SendMoneyService

object ServiceGenerator {

    private val baseUrl = "http://careers.picpay.com/tests/mobdev/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun sendMoneyService() = retrofit.create(SendMoneyService::class.java)
}
