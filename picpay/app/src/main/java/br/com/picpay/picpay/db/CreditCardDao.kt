package br.com.picpay.picpay.db

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface CreditCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save (creditCard: CreditCard)

    @Query("DELETE FROM credit_card")
    fun remove ()

    @Query("SELECT * FROM credit_card")
    fun getCreditCard(): Single<List<CreditCard>>
}