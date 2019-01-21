package com.jvtnascimento.picpay.presenter.contracts

import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.view.contracts.CreditCardViewContractInterface

interface CreditCardPresenterContractInterface {
    fun attach(view: CreditCardViewContractInterface)
    fun create(creditCard: CreditCard)
    fun update(creditCard: CreditCard)
    fun findOne()
}