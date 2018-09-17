package com.picpay.david.davidrockpicpay.models

import com.google.gson.annotations.SerializedName

class Transaction {
    @SerializedName("id")
    var Id: Int = 0
    @SerializedName("timestamp")
    var Timestamp: Long = 0
    @SerializedName("value")
    var Value: Double = 0.toDouble()
    @SerializedName("destination_user")
    var DestinationUser: DestinationUser? = null
    @SerializedName("success")
    var Success: Boolean = false
    @SerializedName("status")
    var Status: String? = null
}