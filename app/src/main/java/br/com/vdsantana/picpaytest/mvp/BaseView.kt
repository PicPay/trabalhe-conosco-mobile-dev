package br.com.vdsantana.picpaytest.mvp

/**
 * Created by vd_sa on 30/03/2018.
 */
interface BaseView {
    fun onError()
    fun setPresenter(presenter: BasePresenter<*>)
}