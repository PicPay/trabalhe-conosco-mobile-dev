package com.picpay.david.davidrockpicpay.features.sendMoney

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication.Companion.boxStore
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import com.picpay.david.davidrockpicpay.models.TransactionModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import com.picpay.david.davidrockpicpay.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SendMoneyPresenter : BasePresenter<SendMoneyMvpView>() {


    fun getCreditCard() {
        //Get CreditCard from ObjectBox Database
        val box: Box<CreditCard> = boxStore.boxFor()
        mvpView?.updateCreditCardSection(box.all.firstOrNull())
    }

    fun sendMoney(model: TransactionModel){
        checkViewAttached()

        val call = DavidRockPicPayApplication.api.SendMoney(model)
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mvpView?.hideLoading()
                    if (it != null) {
                        mvpView?.showReceipt(it)
                    } else {
                        //mvpView?.showError()
                        mvpView?.hideLoading()
                    }

                }, {
                    mvpView?.showError(it.message)
                    mvpView?.hideLoading()
                })
    }
}