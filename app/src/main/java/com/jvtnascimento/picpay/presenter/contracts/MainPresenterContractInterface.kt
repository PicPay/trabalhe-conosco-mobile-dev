package com.jvtnascimento.picpay.presenter.contracts

import com.jvtnascimento.picpay.models.TransactionRequest
import com.jvtnascimento.picpay.ui.contracts.MainViewContractInterface

interface MainPresenterContractInterface {
    fun getUsers() {}
    fun pay(transaction: TransactionRequest)
    fun attach(view: MainViewContractInterface)
}