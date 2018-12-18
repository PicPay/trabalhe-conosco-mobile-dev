package br.com.kassianoresende.picpay.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CreditCardDao {

    @Query("SELECT * FROM credit_card")
    fun getAll(): List<CreditCard>

    @Insert
    fun insert(card: CreditCard):Long
}