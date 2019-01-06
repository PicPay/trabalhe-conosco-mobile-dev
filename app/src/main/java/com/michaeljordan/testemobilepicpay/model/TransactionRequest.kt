package com.michaeljordan.testemobilepicpay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionRequest(
    @SerializedName("card_number")
    var cardNumber: String,
    var cvv: String,
    @SerializedName("expiry_date")
    var expiryDate: String,
    var value: Double,
    @SerializedName("destination_user_id")
    var destinationUserId: Int
) : Parcelable