package br.com.vdsantana.picpaytest.users.presenter

import br.com.vdsantana.picpaytest.mvp.BaseView
import br.com.vdsantana.picpaytest.users.User

/**
 * Created by vd_sa on 30/03/2018.
 */
interface UserView : BaseView {
    fun onUsersRequestResponse(users: List<User>)
    fun showProgress()
    fun hideProgress()
    fun noResult()
}