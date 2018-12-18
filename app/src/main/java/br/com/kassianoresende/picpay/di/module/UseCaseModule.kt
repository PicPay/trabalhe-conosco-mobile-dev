package br.com.kassianoresende.picpay.di.module

import br.com.kassianoresende.picpay.repository.CreditCardRepository
import br.com.kassianoresende.picpay.repository.UserRepository
import br.com.kassianoresende.picpay.usecase.GetCreditCardsUseCase
import br.com.kassianoresende.picpay.usecase.GetUsersUseCase
import br.com.kassianoresende.picpay.usecase.SaveCreditCardUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UseCaseModule{

    @Provides
    @Reusable
    @JvmStatic
    fun provideUserUseCase(repository: UserRepository)=
        GetUsersUseCase(repository)


    @Provides
    @Reusable
    @JvmStatic
    fun provideSaveCreditCardUseCase(repository: CreditCardRepository)=
        SaveCreditCardUseCase(repository)

    @Provides
    @Reusable
    @JvmStatic
    fun provideGetCreditCardUseCase(repository: CreditCardRepository)=
        GetCreditCardsUseCase(repository)

}
