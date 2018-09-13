package com.picpay.david.davidrockpicpay.features.creditCard

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.BasePresenter
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class CreditCardsPresenter : BasePresenter<CreditCardsMvpView>(){

    fun getAllCards(){
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()
        mvpView?.fillList(box.all)
    }
}