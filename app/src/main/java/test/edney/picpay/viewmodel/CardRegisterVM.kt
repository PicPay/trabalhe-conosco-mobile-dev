package test.edney.picpay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.fabric.sdk.android.services.concurrency.AsyncTask
import test.edney.picpay.database.AppDatabase
import test.edney.picpay.database.CardEntity

class CardRegisterVM(application: Application) : AndroidViewModel(application){

    private val database = AppDatabase.get(application)

    fun addCard(card: CardEntity){
        AsyncTask.execute {
            database?.cardDao()?.addCard(card)
        }
    }
}