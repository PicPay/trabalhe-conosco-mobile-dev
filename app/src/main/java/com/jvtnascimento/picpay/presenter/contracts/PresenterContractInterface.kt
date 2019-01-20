package com.jvtnascimento.picpay.presenter.contracts

import com.jvtnascimento.picpay.view.contracts.ViewContractInterface

interface PresenterContractInterface {
    fun getUsers()
    fun attach(view: ViewContractInterface)
}