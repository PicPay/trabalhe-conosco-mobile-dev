package com.picpay.david.davidrockpicpay.models

import com.google.gson.annotations.SerializedName


open class TransactionModel {

    constructor(CardNumber: String?, Cvv: Int?, Value: Double?, ExpiryDate: String?, DestinationUserId: Int) {
        this.CardNumber = CardNumber
        this.Cvv = Cvv
        this.Value = Value
        this.ExpiryDate = ExpiryDate
        this.DestinationUserId = DestinationUserId
    }

    @SerializedName("card_number")
    var CardNumber: String? = null
    @SerializedName("cvv")
    var Cvv: Int? = null
    @SerializedName("value")
    var Value: Double? = null
    @SerializedName("expiry_date")
    var ExpiryDate: String? = null
    @SerializedName("destination_user_id")
    var DestinationUserId: Int = 0
}