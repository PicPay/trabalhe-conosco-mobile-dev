package com.jvtnascimento.picpay.presenter

import android.content.Context
import com.jvtnascimento.picpay.application.BaseApplication
import com.jvtnascimento.picpay.db.CreditCardService
import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.presenter.contracts.CreditCardPresenterContractInterface
import com.jvtnascimento.picpay.view.contracts.CreditCardViewContractInterface
import javax.inject.Inject

class CreditCardPresenter(context: Context): CreditCardPresenterContractInterface {

    @Inject lateinit var creditCardService: CreditCardService

    lateinit var view: CreditCardViewContractInterface

    init {
        (context as BaseApplication).component.inject(this)
    }

    override fun attach(view: CreditCardViewContractInterface) {
        this.view = view
    }

    override fun create(creditCard: CreditCard) {
        this.creditCardService.create(creditCard)
        this.view.returnSuccess(creditCard)
    }

    override fun update(creditCard: CreditCard) {
        this.creditCardService.update(creditCard)
        this.view.returnSuccess(creditCard)
    }

    override fun findOne() {
        val creditCard = this.creditCardService.findOne()
        this.view.getCreditCard(creditCard)
    }
}