package br.com.kassianoresende.picpay.ui.viewstate

sealed class CardListState {

    object Sucess: CardListState()
    object LoadError: CardListState()
    object StartLoading: CardListState()
    object FinishLoading: CardListState()
    object NoCardsFound: CardListState()


}