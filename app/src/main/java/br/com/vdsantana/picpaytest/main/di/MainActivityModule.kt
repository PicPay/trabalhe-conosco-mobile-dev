package br.com.vdsantana.picpaytest.main.di

import br.com.vdsantana.picpaytest.api.ApiInterface
import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.users.presenter.UserPresenter
import br.com.vdsantana.picpaytest.utils.AppSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by vd_sa on 30/03/2018.
 */
@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    internal fun providesUserPresenter(api: ApiInterface, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): UserPresenter = UserPresenter(api, disposable, scheduler)
}