package rodolfogusson.testepicpay.payment.model.creditcard

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "creditCardsTable")
data class CreditCard(@PrimaryKey(autoGenerate = true) val id: Long,
                      @ColumnInfo(name = "cardNumber") val cardNumber: String,
                      @ColumnInfo(name = "cardName") val cardName: String,
                      @ColumnInfo(name = "expiryDate") val expiryDate: LocalDate,
                      @ColumnInfo(name = "cvv") val cvv: String)