package br.com.picpay.picpay.repository

import br.com.picpay.picpay.PicpayApplication
import br.com.picpay.picpay.db.CreditCard
import br.com.picpay.picpay.db.CreditCardDao
import io.reactivex.Single
import javax.inject.Inject

class CreditCardRepository {

    @Inject
    lateinit var creditCardDao: CreditCardDao

    init {
        PicpayApplication.graph.inject(this)
    }

    fun saveCreditCard(creditCard: CreditCard) {
        creditCardDao.remove()
        creditCardDao.save(creditCard)
    }

    fun getCreditCard(): Single<List<CreditCard>> {
        return creditCardDao.getCreditCard()
    }
}