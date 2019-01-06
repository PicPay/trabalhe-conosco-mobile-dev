package com.michaeljordan.testemobilepicpay.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.michaeljordan.testemobilepicpay.model.TransactionRequest
import com.michaeljordan.testemobilepicpay.model.TransactionResponse
import com.michaeljordan.testemobilepicpay.repository.TransactionRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TransactionRepository.TransactionRepositoryProvider.provideTransactionRepository()
    private lateinit var transactionResult: MutableLiveData<TransactionResponse>

    fun getTransactionObservable(): MutableLiveData<TransactionResponse> {
        if (!::transactionResult.isInitialized) {
            transactionResult = MutableLiveData()
        }
        return transactionResult
    }

    fun doTransaction(transactionRequest: TransactionRequest) {
        val callTransaction = repository.doTransaction(transactionRequest)

        callTransaction.enqueue(object : Callback<TransactionResponse> {
            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                Log.d("PicPay", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                transactionResult.value = response.body()
            }

        })
    }
}