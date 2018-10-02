package com.example.igor.roompersistence.database

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.example.igor.picpayandroidx.R
import com.example.igor.picpayandroidx.View.InsertCardActivity
import com.example.igor.picpayandroidx.View.InsertValueActivity
import com.example.igor.roompersistence.local.CardDAO
import com.example.igor.roompersistence.local.CardDataBase

class CardRepository(application: Application) {

    private val mCardDAO: CardDAO
    internal val allCards: LiveData<List<Card>>

    init {
        val db = CardDataBase.getDatabase(application)
        mCardDAO = db!!.cardDAO()
        allCards = mCardDAO.getAllCards()
    }

    fun insert(word: Card) {
        insertAsyncTask(mCardDAO).execute(word)
    }

    fun checkDbAndStartActivity(context: Context, transactionRequest: TransactionRequest) {
        checkDbAndStartActivityAsyncTask(mCardDAO, context, transactionRequest).execute()
    }

    fun deleteCardById(id: Int) {
        deleteCardByIdAsyncTask(mCardDAO).execute(id)
    }

    fun getCardById(id:Int) {
        getCardByIdAsyncTask(mCardDAO).execute()
    }
    private class insertAsyncTask(private val mAsyncTaskDao: CardDAO) : AsyncTask<Card, Void, Void>() {

        override fun doInBackground(vararg params: Card): Void? {
            mAsyncTaskDao.insertCard(params[0])
            return null
        }
    }

    private class checkDbAndStartActivityAsyncTask(private val mAsyncTaskDao: CardDAO, val context: Context, val transactionRequest: TransactionRequest) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg p0: Void?): Boolean {
            return mAsyncTaskDao.getCardsAsList().isEmpty()

        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)

            var intent: Intent
            val transactionRequestKey = context.resources.getString(R.string.transaction_request_parcelable)
            val destinationUserIdKey = context.resources.getString(R.string.destination_user_id_key)

            if (result!!) {

                intent = Intent(context, InsertCardActivity::class.java)
                intent.putExtra(destinationUserIdKey, transactionRequest.destination_user_id)
                context.startActivity(intent)

            } else {

                intent = Intent(context, InsertValueActivity::class.java)
                intent.putExtra(transactionRequestKey, transactionRequest)
                context.startActivity(intent)
            }

        }
    }

    private class deleteCardByIdAsyncTask(private val mAsyncTaskDao: CardDAO) : AsyncTask<Int, Void, Void>() {

        override fun doInBackground(vararg p0: Int?): Void? {
            mAsyncTaskDao.deleteCardById(p0[0]!!)
            return null
        }

    }

    private class getCardByIdAsyncTask(val mAsyncTaskDao: CardDAO) : AsyncTask<Int, Void, Card>() {

        override fun doInBackground(vararg id: Int?): Card {
            return mAsyncTaskDao.getCardById(id[0])
        }

    }
}