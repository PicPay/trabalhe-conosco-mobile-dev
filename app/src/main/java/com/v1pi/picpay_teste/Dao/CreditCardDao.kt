package com.v1pi.picpay_teste.Dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.v1pi.picpay_teste.Domains.CreditCard

@Dao
interface CreditCardDao {
    @Query("SELECT * FROM creditcard")
    fun getAll() : List<CreditCard>

    @Query("SELECT * FROM creditcard WHERE uid = :id")
    fun findById(id : Int) : CreditCard

    @Insert
    fun insertAll(vararg creditsCards : CreditCard)

    @Delete
    fun delete(creditCard : CreditCard)
}