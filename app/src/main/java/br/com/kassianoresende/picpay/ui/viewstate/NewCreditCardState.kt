package br.com.kassianoresende.picpay.ui.viewstate

sealed class NewCreditCardState {

    object Sucess: NewCreditCardState()
    object GenericError: NewCreditCardState()
    object EmptyCardNumber: NewCreditCardState()
    object InvalidCardNumber: NewCreditCardState()
    object EmptyName:NewCreditCardState()
    object EmptyDueDate:NewCreditCardState()
    object InvalidDueDate: NewCreditCardState()
    object EmptyCvv:NewCreditCardState()
}