package com.picpay.david.davidrockpicpay.features.UsersList

import com.picpay.david.davidrockpicpay.features.base.MvpView
import com.picpay.david.davidrockpicpay.models.User

interface ListUsersMvpView : MvpView {

    fun getAllUsers()
    fun hideLoading()
    fun showError(message: String?)
    fun fillList(users : List<User>)
}