package br.com.picpay.picpay.di.component

import android.app.Application
import br.com.picpay.picpay.di.module.DatabaseModule
import br.com.picpay.picpay.di.module.NetworkModule
import br.com.picpay.picpay.repository.CreditCardRepository
import br.com.picpay.picpay.ui.contact.ContactViewModel
import br.com.picpay.picpay.ui.priming.PrimingViewModel
import br.com.picpay.picpay.ui.register.RegisterCreditCardViewModel
import br.com.picpay.picpay.ui.transaction.TransactionViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject (application: Application)

    fun inject (contactViewModel: ContactViewModel)

    fun inject (primingViewModel: PrimingViewModel)

    fun inject (registerCreditCardViewModel: RegisterCreditCardViewModel)

    fun inject (creditCardRepository: CreditCardRepository)

    fun inject (transactionViewModel: TransactionViewModel)
}