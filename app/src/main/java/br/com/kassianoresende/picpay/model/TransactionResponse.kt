package br.com.kassianoresende.picpay.model

import com.google.gson.annotations.SerializedName

class TransactionResponse(
    val transaction: PayResponse
)


class PayResponse(
    val id: Int,
    val timestamp: Int,
    val value: Double,
    @SerializedName("destination_user")
    val destinationUser: User,
    val success: Boolean,
    val status: String
)