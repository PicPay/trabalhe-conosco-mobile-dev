package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.UserDataDao
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.database.DatabaseHelper
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData

class UserDataRepository(application: Application) {

    private var mUserDataDao: UserDataDao? = null
    private var mSaldo: LiveData<UserData>? = null

    init {
        val database: DatabaseHelper = DatabaseHelper.getDatabase(application)
        mUserDataDao = database.userDataDao()
        mSaldo = mUserDataDao!!.getSaldo()
    }

    fun getSaldo() : LiveData<UserData> {
        return mSaldo!!
    }

    fun insert(saldo: UserData) {
        InsertSaldo(mUserDataDao!!).execute(saldo)
    }

    fun update(saldo: Double) {
        UpdateSaldo(mUserDataDao!!).execute(saldo)
    }

    companion object {
        class InsertSaldo(saldo: UserDataDao) : AsyncTask<UserData, Void, Void>() {

            private var mAsyncTaskSaldo: UserDataDao? = saldo

            override fun doInBackground(vararg params: UserData?): Void? {
                mAsyncTaskSaldo!!.insert(params[0]!!)
                return null
            }

        }

        class UpdateSaldo(saldo: UserDataDao) : AsyncTask<Double, Void, Void>() {

            private var mAsyncTask: UserDataDao? = saldo

            override fun doInBackground(vararg params: Double?): Void? {
                mAsyncTask!!.update(params[0]!!)
                return null
            }

        }
    }

}