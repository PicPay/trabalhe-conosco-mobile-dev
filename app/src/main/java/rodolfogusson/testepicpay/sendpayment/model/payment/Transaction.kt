package rodolfogusson.testepicpay.sendpayment.model.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import rodolfogusson.testepicpay.sendpayment.model.contact.Contact
import java.math.BigDecimal

@Parcelize
data class Transaction(
    val id: Int,
    val timestamp: Long,
    val value: BigDecimal,
    @SerializedName("destination_user") val destinationUser: Contact,
    val success: Boolean,
    val status: String
) : Parcelable {
    companion object { const val key = "TRANSACTION_KEY" }
}