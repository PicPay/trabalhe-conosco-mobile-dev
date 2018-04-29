package com.v1pi.picpay_teste.Controllers

import android.view.View
import android.widget.Toast
import com.v1pi.picpay_teste.CreateCreditCardActivity
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_credit_card.*
import java.util.concurrent.Callable

class CreateCreditCardController(private val activity : CreateCreditCardActivity) {
    private val databaseManager: DatabaseManager? = DatabaseManager.getInstance(activity)

    fun insertCreditCard(creditCard: CreditCard) {
        activity.create_credit_card_progress.visibility = View.VISIBLE


        Observable.fromCallable(object : Callable<Unit>  {
            override fun call() {
                databaseManager?.creditCardDao()?.insert(creditCard)
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<Unit?> {
                    override fun accept(t: Unit) {
                        activity.create_credit_card_progress.visibility = View.GONE
                        Toast.makeText(activity, activity.getString(R.string.message_to_insert_sucess), Toast.LENGTH_SHORT).show()
                        activity.clearFormFields()
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable) {
                        activity.create_credit_card_progress.visibility = View.GONE
                        Toast.makeText(activity, activity.getString(R.string.message_to_insert_failed), Toast.LENGTH_SHORT).show()
                    }
                })
    }



}