package com.jvtnascimento.picpay.presenter

import com.jvtnascimento.picpay.models.Transaction
import com.jvtnascimento.picpay.models.TransactionRequest
import com.jvtnascimento.picpay.models.TransactionResponse
import com.jvtnascimento.picpay.models.User
import com.jvtnascimento.picpay.presenter.contracts.MainPresenterContractInterface
import com.jvtnascimento.picpay.services.api.ApiServiceInterface
import com.jvtnascimento.picpay.view.contracts.MainViewContractInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter: MainPresenterContractInterface {

    lateinit var view: MainViewContractInterface

    override fun attach(view: MainViewContractInterface) {
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

    override fun pay(transaction: TransactionRequest) {
        this.view.showProgressBar()
        ApiServiceInterface
            .create()
            .pay(transaction)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result : TransactionResponse ->
                this.view.hideProgressBar()
                this.view.showResult(result)
            }, { error ->
                error.printStackTrace()
                this.view.hideProgressBar()
                this.view.showError(error)
            })
    }
}