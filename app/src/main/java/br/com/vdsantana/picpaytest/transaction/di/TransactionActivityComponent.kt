package br.com.vdsantana.picpaytest.transaction.di

import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.di.component.AppComponent
import br.com.vdsantana.picpaytest.transaction.TransactionActivity
import dagger.Component

/**
 * Created by vd_sa on 30/03/2018.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(TransactionActivityModule::class))
interface TransactionActivityComponent {

    fun inject(transactionActivity: TransactionActivity)
}