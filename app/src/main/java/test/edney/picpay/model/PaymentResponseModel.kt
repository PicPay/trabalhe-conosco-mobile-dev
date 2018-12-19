package test.edney.picpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentResponseModel {
      @SerializedName("transaction")
      @Expose
      var transaction: TransactionModel? = null
}