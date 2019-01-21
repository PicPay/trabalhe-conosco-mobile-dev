package com.jvtnascimento.picpay.view.contracts
import com.jvtnascimento.picpay.models.CreditCard

interface CreditCardViewContractInterface: BaseContractInterface {
    fun getCreditCard(creditCard: CreditCard?) {}
    fun returnSuccess(creditCard: CreditCard) {}
}