package rodolfogusson.testepicpay.payment.model.creditcard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "creditCardsTable")
data class CreditCard(@PrimaryKey(autoGenerate = true) val id: Long,
                      @ColumnInfo(name = "cardNumber") val cardNumber: String,
                      @ColumnInfo(name = "cardName") val cardName: String,
                      @ColumnInfo(name = "expiryDate") val expiryDate: LocalDate,
                      @ColumnInfo(name = "cvv") val cvv: String)