package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.AllTransactionsDao
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.database.DatabaseHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions

class AllTransactionsRepository(application: Application) {

    private var mAllTransactionsDao: AllTransactionsDao? = null
    private var mTransactions: LiveData<List<AllTransactions>>? = null

    init {
        val database: DatabaseHelper = DatabaseHelper.getDatabase(application)
        mAllTransactionsDao = database.allTransactionsDao()
        mTransactions = mAllTransactionsDao!!.getTransactions()
    }

    fun getTransactions() : LiveData<List<AllTransactions>> {
        return mTransactions!!
    }

    fun insert(transactions: AllTransactions) {
        InsertTransaction(mAllTransactionsDao!!).execute(transactions)
    }

    companion object {
        class InsertTransaction(transactions: AllTransactionsDao) : AsyncTask<AllTransactions, Void, Void>() {

            private var mAsyncTaskDao: AllTransactionsDao? = transactions

            override fun doInBackground(vararg params: AllTransactions?): Void? {
                mAsyncTaskDao!!.insert(params[0]!!)
                return null
            }

        }
    }

}