package com.jvtnascimento.picpay.dagger.modules

import android.content.Context
import com.jvtnascimento.picpay.services.repository.CreditCardRepository
import com.jvtnascimento.picpay.services.repository.DBOpenHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CreditCardServiceModule(var context: Context) {

    @Provides
    @Singleton
    fun provideCreditCardService(): CreditCardRepository{
        return CreditCardRepository(DBOpenHelper.getInstance(context).writableDatabase)
    }
}