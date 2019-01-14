package rodolfogusson.testepicpay.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rodolfogusson.testepicpay.core.network.services.PaymentService

object ServiceGenerator {

    private const val baseUrl = "http://careers.picpay.com/tests/mobdev/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun paymentService() = retrofit.create(PaymentService::class.java)
}
