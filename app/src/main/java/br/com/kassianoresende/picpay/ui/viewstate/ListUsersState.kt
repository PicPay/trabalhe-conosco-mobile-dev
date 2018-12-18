package br.com.kassianoresende.picpay.ui.viewstate

sealed class ListUsersState {
    object Sucess: ListUsersState()
    object LoadError: ListUsersState()
    object StartLoading: ListUsersState()
    object FinishLoading: ListUsersState()
}