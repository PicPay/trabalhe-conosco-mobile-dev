package com.michaeljordan.testemobilepicpay.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import com.michaeljordan.testemobilepicpay.data.local.AppDataBase
import com.michaeljordan.testemobilepicpay.data.local.dao.CardDao
import com.michaeljordan.testemobilepicpay.model.Card
import java.lang.Exception
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CardRepository(private val cardDao: CardDao, private val executorService: ExecutorService) {
    companion object {
        @Volatile
        private var INSTANCE: CardRepository? = null

        fun getInstance(application: Application): CardRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CardRepository(
                    AppDataBase.getAppDatabase(application).cardDao(),
                    Executors.newSingleThreadExecutor()
                ).also { INSTANCE = it }
            }

    }

    fun save(card: Card) {
        executorService.execute { cardDao.save(card) }
    }

    fun getCard(): LiveData<Card?> {
       return executorService.submit(cardDao::getCard).get()
    }


}