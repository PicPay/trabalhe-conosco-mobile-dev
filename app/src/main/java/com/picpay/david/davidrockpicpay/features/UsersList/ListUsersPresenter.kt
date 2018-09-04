package com.picpay.david.davidrockpicpay.features.UsersList

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListUsersPresenter : BasePresenter<ListUsersMvpView>() {

    fun getAllUsers() {
        checkViewAttached()
        mvpView?.getAllUsers()

        val call = DavidRockPicPayApplication.api.GetUsers()
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mvpView?.hideLoading()
                    if (it != null) {
                        mvpView?.fillList(it)
                    } else {
                        mvpView?.showError("Não foi possível baixar lista de usuários")
                    }

                }, {
                    mvpView?.showError(it.message)
                    mvpView?.hideLoading()
                })

    }

}