package com.jvtnascimento.picpay.ui.contracts
import com.jvtnascimento.picpay.models.CreditCard

interface CreditCardViewContractInterface: BaseContractInterface {
    fun getCreditCard(creditCard: CreditCard?) {}
    fun returnSuccess(creditCard: CreditCard) {}
}