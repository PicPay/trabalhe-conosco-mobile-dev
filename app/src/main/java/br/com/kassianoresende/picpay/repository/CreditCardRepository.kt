package br.com.kassianoresende.picpay.repository

import br.com.kassianoresende.picpay.model.CreditCard
import io.reactivex.Observable

interface CreditCardRepository {

    fun saveCard(card:CreditCard):Long

    fun getCards(): Observable<List<CreditCard>>
}