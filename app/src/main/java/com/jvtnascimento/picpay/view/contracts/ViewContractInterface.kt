package com.jvtnascimento.picpay.view.contracts
import com.jvtnascimento.picpay.models.User

interface ViewContractInterface {
    fun showUsers(users: ArrayList<User>) {}
    fun showError(error: Throwable)
    fun showProgressBar()
    fun hideProgressBar()
}