package test.edney.picpay.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import test.edney.picpay.api.model.UserModel

interface ApiRequest {

    @Headers("Content-Type: application/json")
    @GET(EndPoint.getUser)
    fun getUsers(): Call<UserModel>

    @Headers("Content-Type: application/json")
    @POST(EndPoint.postPayment)
    fun authStepTwo(@Body registerParams: RequestBody): Call<ResponseBody>

}