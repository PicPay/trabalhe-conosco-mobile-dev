package br.com.vdsantana.picpaytest.transaction.di

import br.com.vdsantana.picpaytest.api.ApiInterface
import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.transaction.presenter.TransactionPresenter
import br.com.vdsantana.picpaytest.utils.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by vd_sa on 30/03/2018.
 */
@Module
class TransactionActivityModule {

    @Provides
    @ActivityScope
    internal fun providesTransactionPresenter(api: ApiInterface, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): TransactionPresenter = TransactionPresenter(api, disposable, scheduler)
}