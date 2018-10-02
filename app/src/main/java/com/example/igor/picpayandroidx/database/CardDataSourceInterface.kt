package com.example.igor.roompersistence.database

import com.example.igor.picpayandroidx.Model.Card
import io.reactivex.Flowable

interface CardDataSourceInterface {
    val allCards : Flowable<List<Card>>
    fun getCardByNumber(number : Int) : Flowable<Card>
    fun insertCard(vararg cards : Card)
    fun updateCard(vararg cards : Card)
    fun deleteCard(card : Card)
    fun deleteAllCards()
}