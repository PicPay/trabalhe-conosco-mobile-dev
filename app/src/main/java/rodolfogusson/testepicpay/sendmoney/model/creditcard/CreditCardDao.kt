package rodolfogusson.testepicpay.sendmoney.model.creditcard

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface CreditCardDao {

    @Insert(onConflict = REPLACE)
    fun insert(card: CreditCard)

    @Query("SELECT * FROM creditCardsTable")
    fun getAllCreditCards() : LiveData<List<CreditCard>>
}