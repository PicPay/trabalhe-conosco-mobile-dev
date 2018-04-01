package br.com.vdsantana.picpaytest.transaction.presenter

import br.com.vdsantana.picpaytest.api.ApiInterface
import br.com.vdsantana.picpaytest.mvp.BasePresenter
import br.com.vdsantana.picpaytest.transaction.TransactionRequest
import br.com.vdsantana.picpaytest.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by vd_sa on 30/03/2018.
 */
class TransactionPresenter @Inject constructor(var api: ApiInterface, disposable: CompositeDisposable, scheduler: SchedulerProvider) : BasePresenter<TransactionView>(disposable, scheduler) {

    fun sendTransaction(cardNumber: String?, cvv: Int, value: Double, expiryDate: String?, destinationUserId: Int?) {
        view?.showProgress()
        disposable.add(api.sendTransaction(TransactionRequest(cardNumber, cvv, value, expiryDate, destinationUserId))
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        { result ->
                            view?.onTransactionRequestSuccess(result)
                        },
                        { _ ->
                            view?.onError()
                        })
        )
    }
}