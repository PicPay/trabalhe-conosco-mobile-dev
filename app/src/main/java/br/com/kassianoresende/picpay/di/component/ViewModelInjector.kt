package br.com.kassianoresende.picpay.di.component

import br.com.kassianoresende.picpay.di.module.NetworkModule
import br.com.kassianoresende.picpay.di.module.RepositoryModule
import br.com.kassianoresende.picpay.di.module.UseCaseModule
import br.com.kassianoresende.picpay.ui.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
                        UseCaseModule::class,
                        RepositoryModule::class,
                        NetworkModule::class])
interface ViewModelInjector {
    fun inject(mainViewModel: MainViewModel)
}