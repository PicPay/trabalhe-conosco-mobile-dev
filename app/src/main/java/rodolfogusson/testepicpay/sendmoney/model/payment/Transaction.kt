package rodolfogusson.testepicpay.sendmoney.model.payment

import com.google.gson.annotations.SerializedName
import rodolfogusson.testepicpay.sendmoney.model.contact.Contact
import java.math.BigDecimal

data class Transaction(
    val id: Int,
    val timestamp: Long,
    val value: BigDecimal,
    @SerializedName("destination_user") val destinationUser: Contact,
    val success: Boolean,
    val status: String
)