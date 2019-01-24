package br.com.picpay.picpay.remote

import br.com.picpay.picpay.model.ResponseTransaction
import br.com.picpay.picpay.model.Payment
import br.com.picpay.picpay.model.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EndPoints {

    @GET("users")
    fun getUsers(): Observable<List<User>>

    @POST("transaction")
    fun sendTransaction(@Body payment: Payment): Observable<ResponseTransaction>
}