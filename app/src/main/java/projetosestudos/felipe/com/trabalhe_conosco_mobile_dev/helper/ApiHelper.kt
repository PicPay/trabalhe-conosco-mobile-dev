package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.Payment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.ResponsePayment
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiHelper {

    @GET("tests/mobdev/users") fun getUsers() : Call<List<User>>

    @POST("tests/mobdev/transaction") fun postPayment(@Body payment: Payment) : Call<ResponsePayment>

    class RetrofitHelper {
        companion object {
            var requestURL = Retrofit.Builder().
                    baseUrl("http://careers.picpay.com/").
                    addConverterFactory(GsonConverterFactory.create()).
                    build()

            fun create() : ApiHelper {
                return requestURL.create(ApiHelper::class.java)
            }
        }
    }

}