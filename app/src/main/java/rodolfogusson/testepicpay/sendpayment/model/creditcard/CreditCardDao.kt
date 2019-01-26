package rodolfogusson.testepicpay.sendpayment.model.creditcard

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface CreditCardDao {

    @Insert(onConflict = REPLACE)
    fun insert(card: CreditCard): Long

    @Query("SELECT * FROM creditCardsTable ORDER BY id DESC LIMIT 1")
    fun getLastCardInserted() : LiveData<CreditCard>
}