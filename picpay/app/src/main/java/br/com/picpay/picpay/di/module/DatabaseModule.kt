package br.com.picpay.picpay.di.module

import android.arch.persistence.room.Room
import android.content.Context
import br.com.picpay.picpay.db.CreditCardDao
import br.com.picpay.picpay.db.PicpayDatabase
import br.com.picpay.picpay.repository.CreditCardRepository
import br.com.picpay.picpay.utils.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule (private val context: Context) {

    @Singleton
    @Provides
    fun provideDatabase (): PicpayDatabase {
        return Room.databaseBuilder(context,
            PicpayDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideCreditCardDao (picpayDatabase: PicpayDatabase): CreditCardDao {
        return picpayDatabase.creditCardDao()
    }

    @Singleton
    @Provides
    fun provideCreditCardRepository (): CreditCardRepository {
        return CreditCardRepository()
    }
}