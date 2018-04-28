package com.v1pi.picpay_teste.Controllers

import android.content.Intent
import com.google.gson.Gson
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.PaymentStatusActivity

class PaymentStatusController(private val activity : PaymentStatusActivity) {
    val transaction : Transaction

    init {
        val intent = activity.intent

        transaction = Gson().fromJson(intent.getStringExtra("transaction"), Transaction::class.java)

        activity.bindDataTransactionToView(transaction)
    }

}