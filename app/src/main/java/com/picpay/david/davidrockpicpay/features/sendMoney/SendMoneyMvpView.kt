package com.picpay.david.davidrockpicpay.features.sendMoney

import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.MvpView

interface SendMoneyMvpView : MvpView {

    fun updateCreditCardSection(cc: CreditCard?)
    fun showError(message: String?)
}