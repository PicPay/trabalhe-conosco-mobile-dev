package com.michaeljordan.testemobilepicpay.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.michaeljordan.testemobilepicpay.model.Card

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(card: Card)

    @Query("SELECT * FROM card LIMIT 1")
    fun getCard() : LiveData<Card?>
}