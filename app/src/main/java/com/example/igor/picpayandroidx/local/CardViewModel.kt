package com.example.igor.picpayandroidx.local

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.picpayandroidx.Model.TransactionRequest
import com.example.igor.roompersistence.database.CardRepository

class CardViewModel(application: Application) : AndroidViewModel(application){

    private val mRepository: CardRepository

    val allCards: LiveData<List<Card>>

    init {
        mRepository = CardRepository(application)
        allCards = mRepository.allCards
    }

    fun insert(card: Card) {
        mRepository.insert(card)
    }

    fun checkDbAndStartActivity(context: Context, transactionRequest: TransactionRequest){
        return mRepository.checkDbAndStartActivity(context, transactionRequest)
    }

    fun deleteCardById(id: Int) {
        mRepository.deleteCardById(id)
    }

    fun getCardById(id: Int) {
        mRepository.getCardById(id)
    }
}