package br.com.vdsantana.picpaytest.api

import br.com.vdsantana.picpaytest.transaction.TransactionRequest
import br.com.vdsantana.picpaytest.transaction.TransactionResponse
import br.com.vdsantana.picpaytest.users.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by vd_sa on 30/03/2018.
 */
interface ApiInterface {

    @GET("users")
    fun getUsers(): Observable<List<User>>

    @POST("transaction")
    fun sendTransaction(@Body transactionRequest: TransactionRequest): Observable<TransactionResponse>
}