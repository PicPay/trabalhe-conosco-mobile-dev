package com.jvtnascimento.picpay.dagger.modules

import android.content.Context
import com.jvtnascimento.picpay.presenter.CreditCardPresenter
import com.jvtnascimento.picpay.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CreditCardPresenterModule(val context: Context) {

    @Provides
    @Singleton
    fun provideCreditCardPresenter(): CreditCardPresenter {
        return CreditCardPresenter(context)
    }
}