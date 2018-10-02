package com.example.igor.roompersistence.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.igor.picpayandroidx.Model.Card

@Dao
interface CardDAO {

    @Query("SELECT * FROM cards")
    fun getAllCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards")
    fun getCardsAsList(): List<Card>

    @Query("SELECT * FROM cards WHERE id=:id" )
    fun getCardById(id: Int?): Card

    @Query("DELETE FROM cards WHERE id=:id")
    fun deleteCardById(id:Int)

    @Insert
    fun insertCard(card: Card)

    @Update
    fun updateCard(card: Card)

    @Delete
    fun deleteCard(card: Card)

    @Query ("DELETE FROM cards")
    fun deleteAllCards()



}