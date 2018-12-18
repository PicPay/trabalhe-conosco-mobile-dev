package br.com.kassianoresende.picpay.di.component

import br.com.kassianoresende.picpay.di.module.DatabaseModule
import br.com.kassianoresende.picpay.di.module.NetworkModule
import br.com.kassianoresende.picpay.di.module.RepositoryModule
import br.com.kassianoresende.picpay.di.module.UseCaseModule
import br.com.kassianoresende.picpay.ui.viewmodel.CardListViewModel
import br.com.kassianoresende.picpay.ui.viewmodel.CheckoutViewModel
import br.com.kassianoresende.picpay.ui.viewmodel.MainViewModel
import br.com.kassianoresende.picpay.ui.viewmodel.NewCardViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
                        UseCaseModule::class,
                        RepositoryModule::class,
                        NetworkModule::class,
                        DatabaseModule::class])
interface ViewModelInjector {
    fun inject(mainViewModel: MainViewModel)
    fun inject(newCardViewModel: NewCardViewModel)
    fun inject(cardListViewModel: CardListViewModel)
    fun inject(checkoutViewModel: CheckoutViewModel)
}