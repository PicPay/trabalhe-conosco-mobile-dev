package br.com.picpay.picpay.di.component

import android.app.Application
import br.com.picpay.picpay.di.module.NetworkModule
import br.com.picpay.picpay.ui.contact.ContactViewModel
import br.com.picpay.picpay.ui.priming.PrimingViewModel
import br.com.picpay.picpay.ui.register.RegisterCreditCardViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject (application: Application)

    fun inject (contactViewModel: ContactViewModel)

    fun inject (primingViewModel: PrimingViewModel)

    fun inject (registerCreditCardViewModel: RegisterCreditCardViewModel)
}