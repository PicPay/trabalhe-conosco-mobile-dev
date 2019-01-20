package com.jvtnascimento.picpay.presenter

import com.jvtnascimento.picpay.models.User
import com.jvtnascimento.picpay.presenter.contracts.PresenterContractInterface
import com.jvtnascimento.picpay.services.api.ApiServiceInterface
import com.jvtnascimento.picpay.view.contracts.ViewContractInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter: PresenterContractInterface {

    lateinit var view: ViewContractInterface

    override fun attach(view: ViewContractInterface) {
        this.view = view
    }

    override fun getUsers() {
        this.view.showProgressBar()
        ApiServiceInterface
            .create()
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list: ArrayList<User> ->
                this.view.hideProgressBar()
                this.view.showUsers(list)
            }, { error ->
                error.printStackTrace()
                this.view.hideProgressBar()
                this.view.showError(error)
            })
    }
}