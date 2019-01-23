package com.jvtnascimento.picpay.dagger

import com.jvtnascimento.picpay.dagger.modules.CreditCardPresenterModule
import com.jvtnascimento.picpay.dagger.modules.CreditCardServiceModule
import com.jvtnascimento.picpay.dagger.modules.MainPresenterModule
import com.jvtnascimento.picpay.presenter.CreditCardPresenter
import com.jvtnascimento.picpay.ui.views.CreditCardRegisterActivity
import com.jvtnascimento.picpay.ui.views.MainActivity
import com.jvtnascimento.picpay.ui.views.PaymentActivity
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