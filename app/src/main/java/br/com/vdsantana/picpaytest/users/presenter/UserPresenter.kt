package br.com.vdsantana.picpaytest.users.presenter

import br.com.vdsantana.picpaytest.api.ApiInterface
import br.com.vdsantana.picpaytest.mvp.BasePresenter
import br.com.vdsantana.picpaytest.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by vd_sa on 30/03/2018.
 */
class UserPresenter @Inject constructor(var api: ApiInterface, disposable: CompositeDisposable, scheduler: SchedulerProvider) : BasePresenter<UserView>(disposable, scheduler) {

    fun getUsers() {
        view?.showProgress()
        disposable.add(api.getUsers()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(
                        { result ->
                            view?.hideProgress()

                            if (result == null || result.isEmpty())
                                view?.noResult()
                            else
                                view?.onUsersRequestResponse(result)
                        },
                        { _ ->
                            view?.hideProgress()
                            view?.onError()
                        })
        )
    }
}