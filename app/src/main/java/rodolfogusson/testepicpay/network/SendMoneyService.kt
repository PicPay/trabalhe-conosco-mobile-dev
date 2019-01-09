package rodolfogusson.testepicpay.network

import android.content.res.Resources
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rodolfogusson.testepicpay.R

interface SendMoneyService {
    @GET("users")
    fun getUsersList(): Call<List<Unit>>

    companion object Factory {
        fun create(): SendMoneyService {
            val retrofit = Retrofit.Builder()
                .baseUrl(Resources.getSystem().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(SendMoneyService::class.java)
        }
    }
}