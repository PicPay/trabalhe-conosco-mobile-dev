package com.michaeljordan.testemobilepicpay.repository

import com.michaeljordan.testemobilepicpay.data.remote.PicPayApi
import com.michaeljordan.testemobilepicpay.data.remote.RetrofitFactory
import com.michaeljordan.testemobilepicpay.model.TransactionRequest
import com.michaeljordan.testemobilepicpay.model.TransactionResponse
import retrofit2.Call

class TransactionRepository {
    private val service: PicPayApi = RetrofitFactory.create()

    fun doTransaction(transactionRequest: TransactionRequest) : Call<TransactionResponse> {
        return service.doTransaction(transactionRequest)
    }

    object TransactionRepositoryProvider {
        fun provideTransactionRepository(): TransactionRepository {
            return TransactionRepository()
        }
    }
}