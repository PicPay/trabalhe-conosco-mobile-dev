package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.UserCardsDao
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.database.DatabaseHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards

class UserCardsRepository(application: Application) {

    private var mUserCardsDao: UserCardsDao? = null
    private var mCards: LiveData<List<UserCards>>? = null

    init {
        val database: DatabaseHelper = DatabaseHelper.getDatabase(application)
        mUserCardsDao = database.userCardsDao()
        mCards = mUserCardsDao!!.getCards()
    }

    fun getCards() : LiveData<List<UserCards>> {
        return mCards!!
    }

    fun insert(cards: UserCards) {
        InsertCard(mUserCardsDao!!).execute(cards)
    }

    companion object {
        class InsertCard(card: UserCardsDao) : AsyncTask<UserCards, Void, Void>() {

            private var mAsyncTaskCard: UserCardsDao? = card

            override fun doInBackground(vararg params: UserCards?): Void? {
                mAsyncTaskCard!!.insert(params[0]!!)
                return null
            }

        }
    }

}