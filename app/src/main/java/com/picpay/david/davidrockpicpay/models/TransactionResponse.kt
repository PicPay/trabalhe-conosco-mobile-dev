package com.picpay.david.davidrockpicpay.models

import com.google.gson.annotations.SerializedName

class TransactionResponse {
    @SerializedName("transaction")
    var Transaction: Transaction? = null
}