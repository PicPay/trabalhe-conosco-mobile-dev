package br.com.kassianoresende.picpay.ui.viewstate

sealed class PayUserState {

    object Sucess: PayUserState()
    object PayError: PayUserState()
    object StartLoading: PayUserState()
    object FinishLoading: PayUserState()
}