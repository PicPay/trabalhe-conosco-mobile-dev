package rodolfogusson.testepicpay.sendpayment.model.payment

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PaymentRequest(
    @SerializedName("card_number") val cardNumber: String,
    val cvv: Int,
    val value: BigDecimal,
    @SerializedName("expiry_date") val expiryDate: String,
    @SerializedName("destination_user_id") val destinationUserId: Int
)