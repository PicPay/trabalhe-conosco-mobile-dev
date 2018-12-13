package test.edney.picpay.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import test.edney.picpay.database.AppDatabase
import test.edney.picpay.database.CardEntity

class CardRegisterVM(application: Application) : AndroidViewModel(application){

    private val database = AppDatabase.get(application)
    val card = MutableLiveData<CardEntity>()

    init {
        card.value = GetCardTask(database).execute().get()
    }

    fun addCard(card: CardEntity){
        AsyncTask.execute { database?.cardDao()?.addCard(card) }
    }

    class GetCardTask(private val db: AppDatabase?) : android.os.AsyncTask<Void, Void, CardEntity?>() {
        override fun doInBackground(vararg params: Void?): CardEntity? {
            return db?.cardDao()?.getCard()
        }
    }
}