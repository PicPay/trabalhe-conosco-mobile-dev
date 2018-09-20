package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import com.google.gson.annotations.SerializedName

data class Payment(@SerializedName("card_number") val cardNumber: String,
                   @SerializedName("cvv") val cvv: Int,
                   @SerializedName("value") val value: Double,
                   @SerializedName("expiry_date") val expiryDate: String,
                   @SerializedName("destination_user_id") val destinationUserID: Int)