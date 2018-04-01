package br.com.vdsantana.picpaytest.mvp

/**
 * Created by vd_sa on 30/03/2018.
 */
interface Presenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}