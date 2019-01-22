package rodolfogusson.testepicpay.payment.model.creditcard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "creditCardsTable")
data class CreditCard(@ColumnInfo(name = "cardNumber") val cardNumber: String,
                      @ColumnInfo(name = "cardName") val cardName: String,
                      @ColumnInfo(name = "expiryString") val expiryDate: LocalDate,
                      @ColumnInfo(name = "cvv") val cvv: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}