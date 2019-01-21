package com.jvtnascimento.picpay.view.contracts
import com.jvtnascimento.picpay.models.TransactionResponse
import com.jvtnascimento.picpay.models.User

interface MainViewContractInterface: BaseContractInterface {
    fun showUsers(users: ArrayList<User>) {}
    fun showResult(transaction: TransactionResponse) {}
    fun showError(error: Throwable)
}