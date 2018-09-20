package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.repository.AllTransactionsRepository

class AllTransactionsViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var mAllTransactionsRepository: AllTransactionsRepository? = null
    private var mTransactions: LiveData<List<AllTransactions>>? = null

    init {
        mAllTransactionsRepository = AllTransactionsRepository(application)
        mTransactions = mAllTransactionsRepository!!.getTransactions()
    }

    fun getTransactions() : LiveData<List<AllTransactions>> {
        return mTransactions!!
    }

    fun insert(transaction: AllTransactions) {
        mAllTransactionsRepository!!.insert(transaction)
    }

}