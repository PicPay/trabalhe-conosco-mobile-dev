package br.com.kassianoresende.picpay.service

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.model.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("tests/mobdev/users")
    fun getUsers(): Observable<List<User>>

    @POST("tests/mobdev/transaction")
    fun payUser(@Body transaction:PayUserTransaction):Observable<TransactionResponse>

}

