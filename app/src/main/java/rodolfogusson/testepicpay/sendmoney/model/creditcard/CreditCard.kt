package rodolfogusson.testepicpay.sendmoney.model.creditcard

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "creditCardsTable")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "cardNumber") val cardNumber: String,
    @ColumnInfo(name = "cardName") val cardName: String,
    @ColumnInfo(name = "expiryString") val expiryDate: LocalDate,
    @ColumnInfo(name = "cvv") val cvv: String
) : Parcelable {
    companion object { const val key = "CREDIT_CARD_KEY" }
}