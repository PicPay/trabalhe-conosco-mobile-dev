package com.picpay.david.davidrockpicpay.features.creditCard

import com.picpay.david.davidrockpicpay.features.base.MvpView

interface NewCreditCardMvpView : MvpView {
    fun showErrorDialog(message: String?)
}