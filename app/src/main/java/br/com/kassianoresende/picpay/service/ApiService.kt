package br.com.kassianoresende.picpay.service

import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.model.User
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {

    @GET("tests/mobdev/users")
    fun getUsers(): Observable<List<User>>


    @Headers("Content-Type: application/json")
    @POST("tests/mobdev/transaction")
    fun payUser(@Body transaction:RequestBody):Observable<TransactionResponse>

}

