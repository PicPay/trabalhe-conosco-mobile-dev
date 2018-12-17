package br.com.kassianoresende.picpay.di.module

import br.com.kassianoresende.picpay.repository.UserRepository
import br.com.kassianoresende.picpay.usecase.GetUsersUseCase
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


}
