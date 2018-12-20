package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import io.reactivex.Observable

interface PayUserRepository {

    fun payUser(transaction: PayUserTransaction): Observable<TransactionResponse>

}