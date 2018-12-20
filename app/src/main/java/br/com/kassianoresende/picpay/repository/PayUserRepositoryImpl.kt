package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.service.ApiService
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.RequestBody
import javax.inject.Inject

class PayUserRepositoryImpl  @Inject constructor(private val apiService: ApiService):PayUserRepository {

    override fun payUser(transaction: PayUserTransaction): Observable<TransactionResponse> {

        val req = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(transaction)
        )

        return apiService.payUser(req)
    }

}