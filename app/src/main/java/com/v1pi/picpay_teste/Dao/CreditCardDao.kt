package com.v1pi.picpay_teste.Dao

import android.arch.persistence.room.*
import com.v1pi.picpay_teste.Domains.CreditCard
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CreditCardDao {
    @Query("SELECT * FROM creditCard")
    fun getAll() : Flowable<CreditCard>

    @Query("SELECT * FROM creditCard WHERE uid = :id")
    fun findById(id : Int) : Single<CreditCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(creditCard : CreditCard)

    @Delete
    fun delete(creditCard : CreditCard)

    @Query("DELETE FROM creditCard")
    fun deleteTable()
}