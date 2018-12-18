package br.com.kassianoresende.picpay.di.module

import br.com.kassianoresende.picpay.model.AppDatabase
import br.com.kassianoresende.picpay.repository.CreditCardRepository
import br.com.kassianoresende.picpay.repository.CreditCardRepositoryImpl
import br.com.kassianoresende.picpay.repository.UserRepository
import br.com.kassianoresende.picpay.repository.UserRepositoryImpl
import br.com.kassianoresende.picpay.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.Reusable


@Module
object RepositoryModule{

    @Provides
    @Reusable
    @JvmStatic
    fun provideUserRepository(apiService: ApiService): UserRepository =
        UserRepositoryImpl(apiService)


    @Provides
    @Reusable
    @JvmStatic
    fun provideCreditRepository(db: AppDatabase): CreditCardRepository =
        CreditCardRepositoryImpl(db)

}