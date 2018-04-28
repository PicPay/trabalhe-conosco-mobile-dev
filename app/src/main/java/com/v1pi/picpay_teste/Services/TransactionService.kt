package com.v1pi.picpay_teste.Services

import com.v1pi.picpay_teste.Domains.Transaction
import io.reactivex.Single
import retrofit2.http.*

interface TransactionService {
    @POST("transaction")
    fun send(@Body transaction: Transaction) : Single<Transaction>
}