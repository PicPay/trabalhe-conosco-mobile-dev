package com.jvtnascimento.picpay.dagger

import com.jvtnascimento.picpay.presenter.CreditCardPresenter
import com.jvtnascimento.picpay.view.CreditCardRegisterActivity
import com.jvtnascimento.picpay.view.MainActivity
import com.jvtnascimento.picpay.view.PaymentActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPresenterModule::class, CreditCardServiceModule::class, CreditCardPresenterModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(creditCardPresenter: CreditCardPresenter)
    fun inject(creditCardRegisterActivity: CreditCardRegisterActivity)
    fun inject(paymentActivity: PaymentActivity)
}