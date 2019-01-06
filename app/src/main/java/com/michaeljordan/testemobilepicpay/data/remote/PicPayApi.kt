package com.michaeljordan.testemobilepicpay.data.remote

import com.michaeljordan.testemobilepicpay.model.TransactionRequest
import com.michaeljordan.testemobilepicpay.model.TransactionResponse
import com.michaeljordan.testemobilepicpay.model.Contact
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface PicPayApi {
    @GET("tests/mobdev/users")
    fun getUsers(): Call<List<Contact>>

    @POST("tests/mobdev/transaction")
    fun doTransaction(
        @Body transactionRequest: TransactionRequest
    ): Call<TransactionResponse>
}