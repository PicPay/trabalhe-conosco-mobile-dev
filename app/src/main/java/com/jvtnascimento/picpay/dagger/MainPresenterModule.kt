package com.jvtnascimento.picpay.dagger

import com.jvtnascimento.picpay.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainPresenterModule {

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter {
        return MainPresenter()
    }
}