package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.service.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class PayUserRepositoryImpl  @Inject constructor(private val apiService: ApiService):PayUserRepository {

    override fun payUser(transaction: PayUserTransaction): Observable<TransactionResponse> =
        apiService.payUser(transaction)


}