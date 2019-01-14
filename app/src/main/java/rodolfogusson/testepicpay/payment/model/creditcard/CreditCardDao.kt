package rodolfogusson.testepicpay.payment.model.creditcard

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface CreditCardDao {

    @Insert(onConflict = REPLACE)
    fun insert(card: CreditCard)

    @Query("SELECT * FROM creditCardsTable")
    fun getAllCreditCards() : LiveData<List<CreditCard>>
}