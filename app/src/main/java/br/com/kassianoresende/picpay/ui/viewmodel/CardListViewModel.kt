package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.view.View
import br.com.kassianoresende.picpay.model.CreditCard
import br.com.kassianoresende.picpay.ui.adapters.CardsAdapter
import br.com.kassianoresende.picpay.ui.viewstate.CardListState
import br.com.kassianoresende.picpay.usecase.GetCreditCardsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CardListViewModel: BaseViewModel() {


    @Inject
    lateinit var useCase: GetCreditCardsUseCase

    private lateinit var subscription : Disposable

    val viewstate = MutableLiveData<CardListState>()
    val cardAdapter = CardsAdapter()

    val errorClickListener = View.OnClickListener { loadCards() }

    fun loadCards(){
        subscription = useCase
            .getCreditCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCardsStart() }
            .doOnTerminate { onRetrieveCardsFinish() }
            .subscribe(
                { result->
                    onRetrieveCardsSuccess(result)
                }, {
                    onRetrieveCardsError()
                }
            )
    }


    private fun onRetrieveCardsSuccess(result: List<CreditCard>) {

        if(result.size ==0){
            viewstate.value = CardListState.NoCardsFound
        }else {
            cardAdapter.updateCardList(result)
            viewstate.value = CardListState.Sucess
        }
    }

    private fun onRetrieveCardsError() {
        viewstate.value = CardListState.LoadError
    }

    private fun onRetrieveCardsFinish() {
        viewstate.value = CardListState.FinishLoading
    }

    private fun onRetrieveCardsStart() {
        viewstate.value = CardListState.StartLoading
    }


    override fun onCleared() {
        super.onCleared()
        if(::subscription.isInitialized) subscription.dispose()
    }
}