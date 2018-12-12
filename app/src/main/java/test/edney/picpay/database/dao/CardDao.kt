package test.edney.picpay.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.edney.picpay.database.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: CardEntity)

    @Query("SELECT * FROM card LIMIT 1")
    fun hasCard():Boolean

    @Query("SELECT * FROM card LIMIT 1")
    fun getCard(): CardEntity
}