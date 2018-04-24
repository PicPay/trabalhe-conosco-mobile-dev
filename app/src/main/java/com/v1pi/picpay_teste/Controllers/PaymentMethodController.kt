package com.v1pi.picpay_teste.Controllers

import android.os.Bundle
import com.v1pi.picpay_teste.Database.DatabaseManager
import com.v1pi.picpay_teste.Domains.CreditCard
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.Fragments.WithCreditCardFragment
import com.v1pi.picpay_teste.Fragments.WithoutCreditCardFragment
import com.v1pi.picpay_teste.Listeners.FragmentCreditCardListener
import com.v1pi.picpay_teste.PaymentMethodActivity
import com.v1pi.picpay_teste.Utils.DbWorkerThread
import kotlinx.android.synthetic.main.activity_payment_method.*

class PaymentMethodController(private val activity: PaymentMethodActivity) {
    val user : User

    private var mDbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")
    private var databaseManager: DatabaseManager? = null

    init {
        mDbWorkerThread.start()

        activity.fragment_credit_card.setOnClickListener(FragmentCreditCardListener(activity))

        val intent = activity.intent

        user = User(intent.getStringExtra("id").toInt(),
                intent.getStringExtra("name"),
                intent.getStringExtra("img"),
                intent.getStringExtra("username"))

        activity.bindDataUserToView(user)

        databaseManager = DatabaseManager.getInstance(activity)

        getCreditCardsFromDb()

    }

    private fun getCreditCardsFromDb() {
        val task = Runnable {
            val listCreditCard = databaseManager?.creditCardDao()?.getAll()

            if(listCreditCard == null || listCreditCard.isEmpty()){
                activity.changeCreditCardFragment(WithoutCreditCardFragment())
            } else {
                activity.changeCreditCardFragment(createwithCreditCardFragmentWithBundle(listCreditCard[0]))
            }

        }

        mDbWorkerThread.postTask(task)
    }

    fun createwithCreditCardFragmentWithBundle(creditCard : CreditCard) : WithCreditCardFragment {
        val withCreditCardFragment = WithCreditCardFragment()
        val bundle = Bundle()
        bundle.putString("number", creditCard.number)
        withCreditCardFragment.arguments = bundle
        return withCreditCardFragment
    }




}