package com.v1pi.picpay_teste.Controllers

import android.os.Bundle
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Fragments.WithCreditCardFragment
import com.v1pi.picpay_teste.Fragments.WithoutCreditCardFragment
import com.v1pi.picpay_teste.Listeners.FragmentCreditCardListener
import com.v1pi.picpay_teste.PaymentMethodActivity
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_payment_method.*

class PaymentMethodController(private val activity: PaymentMethodActivity) {
    val user : User
    var selectedCreditCard : CreditCard? = null

    private val databaseManager: DatabaseManager? = DatabaseManager.getInstance(activity)

    init {
        activity.fragment_credit_card.setOnClickListener(FragmentCreditCardListener(activity, this))
        activity.changeCreditCardFragment(WithoutCreditCardFragment())

        val intent = activity.intent

        user = User(intent.getStringExtra("id").toInt(),
                intent.getStringExtra("name"),
                intent.getStringExtra("img"),
                intent.getStringExtra("username"))

        activity.bindDataUserToView(user)

        getAndUIFirstCreditCard()

    }

    private fun getAndUIFirstCreditCard() {
        databaseManager?.creditCardDao()?.getAll()?.firstOrError()?.subscribeOn(Schedulers.io())?.subscribe(object : Consumer<CreditCard> {
            override fun accept(t: CreditCard) {
                selectedCreditCard = t
                activity.changeCreditCardFragment(createwithCreditCardFragmentWithBundle())
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable) {
                activity.changeCreditCardFragment(WithoutCreditCardFragment())
            }
        })
    }

    fun createwithCreditCardFragmentWithBundle() : WithCreditCardFragment {
        val withCreditCardFragment = WithCreditCardFragment()
        val bundle = Bundle()
        bundle.putString("number", this.selectedCreditCard?.number)
        withCreditCardFragment.arguments = bundle
        return withCreditCardFragment
    }




}