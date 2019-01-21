package com.jvtnascimento.picpay.dagger

import android.content.Context
import com.jvtnascimento.picpay.db.CreditCardService
import com.jvtnascimento.picpay.db.DBOpenHelper
import com.jvtnascimento.picpay.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CreditCardServiceModule(var context: Context) {

    @Provides
    @Singleton
    fun provideCreditCardService(): CreditCardService{
        return CreditCardService(DBOpenHelper.getInstance(context).writableDatabase)
    }
}