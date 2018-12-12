package test.edney.picpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Int? = null
    @SerializedName("value")
    @Expose
    var value: Double? = null
    @SerializedName("destination_user")
    @Expose
    var destinationUser: DestinationUserModel? = null
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
}