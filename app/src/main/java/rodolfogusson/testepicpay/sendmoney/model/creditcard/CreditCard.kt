package rodolfogusson.testepicpay.sendmoney.model.creditcard

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(tableName = "creditCardsTable")
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "expiryDateField") var expiryDate: LocalDate,
    @ColumnInfo(name = "cvvField") var cvv: String
) : Parcelable {
    companion object { const val key = "CREDIT_CARD_KEY" }
}