package test.edney.picpay.database.dao

import androidx.room.Dao
import androidx.room.Insert
import test.edney.picpay.database.CardEntity

@Dao
interface CardDao {
    @Insert
    fun addCard(card: CardEntity)
}