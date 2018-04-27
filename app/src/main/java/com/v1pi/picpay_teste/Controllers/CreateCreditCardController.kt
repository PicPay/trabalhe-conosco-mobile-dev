package com.v1pi.picpay_teste.Controllers

import android.widget.Toast
import com.v1pi.picpay_teste.CreateCreditCardActivity
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class CreateCreditCardController(private val activity : CreateCreditCardActivity) {
    private val databaseManager: DatabaseManager? = DatabaseManager.getInstance(activity)

    fun insertCreditCard(creditCard: CreditCard) {
        Observable.fromCallable { databaseManager?.creditCardDao()?.insert(creditCard) }.subscribeOn(Schedulers.io())
                .subscribe(object : Consumer<Unit?> {
                    override fun accept(t: Unit) {
                        Toast.makeText(activity, "SUCESSO!", Toast.LENGTH_SHORT).show()
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable) {
                        Toast.makeText(activity, "ERROR!", Toast.LENGTH_SHORT).show()
                    }
                })
    }



}