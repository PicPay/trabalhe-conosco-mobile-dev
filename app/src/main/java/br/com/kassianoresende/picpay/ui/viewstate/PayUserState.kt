package br.com.kassianoresende.picpay.ui.viewstate

sealed class PayUserState {

    object Approved: PayUserState()
    object Unauthorized: PayUserState()
    object PayError: PayUserState()
    object StartLoading: PayUserState()
    object FinishLoading: PayUserState()
}