package com.picpay.david.davidrockpicpay.features.sendMoney

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication.Companion.boxStore
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import com.picpay.david.davidrockpicpay.models.User


class SendMoneyPresenter : BasePresenter<SendMoneyMvpView>() {


    fun getCreditCard() {
        //To get a preference
        val box: Box<CreditCard> = boxStore.boxFor()


        mvpView?.updateCreditCardSection(box.all.firstOrNull())

    }
}