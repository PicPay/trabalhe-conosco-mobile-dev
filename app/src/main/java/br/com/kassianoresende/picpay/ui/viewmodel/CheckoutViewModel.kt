package br.com.kassianoresende.picpay.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import br.com.kassianoresende.picpay.model.PayUserTransaction
import br.com.kassianoresende.picpay.model.TransactionResponse
import br.com.kassianoresende.picpay.ui.viewstate.PayUserState
import br.com.kassianoresende.picpay.usecase.PayUserUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CheckoutViewModel: BaseViewModel() {


    @Inject
    lateinit var useCase: PayUserUseCase

    private lateinit var subscription : Disposable

    val viewstate = MutableLiveData<PayUserState>()

    fun payUsers(transaction:PayUserTransaction){

        subscription = useCase
            .payUser(transaction)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onPayUserStart() }
            .doOnTerminate { onPayUserFinish() }
            .subscribe(
                { result->
                    onPayUserSuccess(result)
                }, {
                    onPayUserError()
                }
            )
    }


    fun onPayUserStart(){
        viewstate.value = PayUserState.StartLoading
    }

    fun onPayUserFinish(){
        viewstate.value = PayUserState.FinishLoading
    }

    fun onPayUserSuccess(response:TransactionResponse){
        viewstate.value = PayUserState.Sucess
    }

    fun onPayUserError(){
        viewstate.value = PayUserState.PayError
    }

    override fun onCleared() {
        super.onCleared()
        if(::subscription.isInitialized) subscription.dispose()
    }


}