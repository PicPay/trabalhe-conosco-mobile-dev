package test.edney.picpay.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import test.edney.picpay.BuildConfig
import test.edney.picpay.model.PaymentResponseModel
import test.edney.picpay.model.UserModel

interface ApiRequest {
      @Headers("Content-Type: application/json")
      @GET(BuildConfig.USERS)
      fun getUsers(): Call<List<UserModel>>

      @Headers("Content-Type: application/json")
      @POST(BuildConfig.TRANSACTION)
      fun sendPayment(@Body registerParams: RequestBody): Call<PaymentResponseModel>
}