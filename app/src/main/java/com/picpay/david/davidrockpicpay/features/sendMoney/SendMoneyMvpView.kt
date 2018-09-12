package com.picpay.david.davidrockpicpay.features.sendMoney

import com.picpay.david.davidrockpicpay.entities.CreditCard
import com.picpay.david.davidrockpicpay.features.base.MvpView
import com.picpay.david.davidrockpicpay.models.TransactionResponse

interface SendMoneyMvpView : MvpView {

    fun updateCreditCardSection(cc: CreditCard?)
    fun showError(message: String?)
    fun hideLoading()
    fun showLoading()
    fun showReceipt(response: TransactionResponse)
}