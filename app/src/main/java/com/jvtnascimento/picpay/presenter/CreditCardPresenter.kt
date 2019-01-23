package com.jvtnascimento.picpay.presenter

import android.content.Context
import com.jvtnascimento.picpay.application.BaseApplication
import com.jvtnascimento.picpay.services.repository.CreditCardRepository
import com.jvtnascimento.picpay.models.CreditCard
import com.jvtnascimento.picpay.presenter.contracts.CreditCardPresenterContractInterface
import com.jvtnascimento.picpay.ui.contracts.CreditCardViewContractInterface
import javax.inject.Inject

class CreditCardPresenter(context: Context): CreditCardPresenterContractInterface {

    @Inject lateinit var creditCardRepository: CreditCardRepository

    lateinit var view: CreditCardViewContractInterface

    init {
        (context as BaseApplication).component.inject(this)
    }

    override fun attach(view: CreditCardViewContractInterface) {
        this.view = view
    }

    override fun create(creditCard: CreditCard) {
        this.creditCardRepository.create(creditCard)
        this.view.returnSuccess(creditCard)
    }

    override fun update(creditCard: CreditCard) {
        this.creditCardRepository.update(creditCard)
        this.view.returnSuccess(creditCard)
    }

    override fun findOne() {
        val creditCard = this.creditCardRepository.findOne()
        this.view.getCreditCard(creditCard)
    }
}