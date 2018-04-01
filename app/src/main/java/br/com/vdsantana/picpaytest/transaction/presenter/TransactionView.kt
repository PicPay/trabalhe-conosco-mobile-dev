package br.com.vdsantana.picpaytest.transaction.presenter

import br.com.vdsantana.picpaytest.mvp.BaseView
import br.com.vdsantana.picpaytest.transaction.TransactionResponse

/**
 * Created by vd_sa on 30/03/2018.
 */
interface TransactionView : BaseView {
    fun onTransactionRequestSuccess(transactionResponse: TransactionResponse)
    fun showProgress()
}