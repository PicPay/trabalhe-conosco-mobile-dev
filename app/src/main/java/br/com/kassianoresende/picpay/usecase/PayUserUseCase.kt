package br.com.kassianoresende.picpay.usecase

import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.repository.PayUserRepository
import javax.inject.Inject

class PayUserUseCase @Inject constructor(private val repository: PayUserRepository) {


    fun payUser(transaction: PayUserTransaction) =
        repository.payUser(transaction)




}