package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.AppDatabase
import br.com.kassianoresende.picpay.model.CreditCard
import io.reactivex.Observable
import javax.inject.Inject

class CreditCardRepositoryImpl @Inject constructor(val db:AppDatabase): CreditCardRepository {

    override fun saveCard(card: CreditCard) =
         db.creditCardDao().insert(card)


    override fun getCards(): Observable<List<CreditCard>> =
        Observable.fromCallable { db.creditCardDao().getAll() }

}