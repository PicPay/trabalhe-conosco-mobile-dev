package com.v1pi.picpay_teste.Utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.v1pi.picpay_teste.Domains.Transaction
import com.v1pi.picpay_teste.Domains.User
import com.v1pi.picpay_teste.PaymentStatusActivity
import com.v1pi.picpay_teste.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RetrofitManager {
    fun responseUsers(success : (List<User>) -> Unit) {
        RESTState.RESPONSE_USER = RESTState.STATES.PROGRESS

        RetrofitConfig().userService.users().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Consumer<List<User>> {
            override fun accept(t: List<User>) {
                success(t)
                RESTState.RESPONSE_USER = RESTState.STATES.READY
            }
        }, object : Consumer<Throwable> {
            override fun accept(t: Throwable) {
                RESTState.RESPONSE_USER = RESTState.STATES.READY
            }
        })
    }

    fun requestTransaction(transaction: Transaction, context: Context) {
        RESTState.REQUEST_TRANSACTION = RESTState.STATES.PROGRESS

        RetrofitConfig(gson = GsonBuilder().registerTypeAdapter(Transaction::class.java, TransactionDeserializer()).create()).transactionService.send(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<Transaction> {
                    override fun accept(t: Transaction) {
                        val intent = Intent(context, PaymentStatusActivity::class.java)
                        var json = Gson().toJson(t)
                        json = json.substring(0, json.length-1) + ",card_number:${transaction.card_number}}"
                        intent.putExtra("transaction", json)
                        context.startActivity(intent)
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable) {
                        Toast.makeText(context, context.getString(R.string.message_to_insert_failed), Toast.LENGTH_SHORT).show()
                    }
                })
    }
}