package com.picpay.david.davidrockpicpay.features.creditCard

import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.MvpView

interface CreditCardsMvpView : MvpView {

    fun hideLoading()
    fun showError(message: String?)
    fun fillList(cards : List<CreditCard>)
}