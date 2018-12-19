package test.edney.picpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentRequestModel {
      @SerializedName("card_number")
      @Expose
      var cardNumber: String? = null
      @SerializedName("cvv")
      @Expose
      var cvv: Int? = null
      @SerializedName("value")
      @Expose
      var value: Double? = null
      @SerializedName("expiry_date")
      @Expose
      var expiryDate: String? = null
      @SerializedName("destination_user_id")
      @Expose
      var destinationUserId: Int? = null
}