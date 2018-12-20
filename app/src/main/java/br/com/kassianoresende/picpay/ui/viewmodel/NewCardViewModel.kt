package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import br.com.kassianoresende.picpay.model.CreditCard
import br.com.kassianoresende.picpay.ui.viewstate.NewCreditCardState
import br.com.kassianoresende.picpay.usecase.SaveCreditCardUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewCardViewModel : BaseViewModel() {

    val viewState = MutableLiveData<NewCreditCardState>()

    @Inject
    lateinit var useCase: SaveCreditCardUseCase

    private lateinit var subscription : Disposable

    fun saveCard(cardNumber:String, name:String, dueDate:String, cvv:Int){

        val card = CreditCard( 0,cardNumber,"Mastercard",name,dueDate, cvv)

        subscription = useCase.saveCreditCard(card)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                onSaveCardSuccess()
            },{
                onSaveCardError()
            }
        )
    }

    fun onSaveCardSuccess(){
        viewState.value = NewCreditCardState.Sucess
    }

    fun onSaveCardError() {
        viewState.value = NewCreditCardState.GenericError
    }


    override fun onCleared() {
        super.onCleared()
        if(::subscription.isInitialized) subscription.dispose()
    }

}