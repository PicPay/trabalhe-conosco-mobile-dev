package br.com.vdsantana.picpaytest.creditcard.di

import br.com.vdsantana.picpaytest.creditcard.add.AddCreditCardActivity
import br.com.vdsantana.picpaytest.creditcard.choose.ChooseCreditCardActivity
import br.com.vdsantana.picpaytest.di.ActivityScope
import br.com.vdsantana.picpaytest.di.component.AppComponent
import dagger.Component

/**
 * Created by vd_sa on 30/03/2018.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(CreditCardModule::class))
interface CreditCardComponent {

    fun inject(addCreditCardActivity: AddCreditCardActivity)
    fun inject(chooseCreditCardActivity: ChooseCreditCardActivity)
}