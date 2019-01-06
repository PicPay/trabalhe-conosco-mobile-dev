package com.michaeljordan.testemobilepicpay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    var id: Int,
    @SerializedName("timestamp")
    var date: String,
    var value: Double,
    @SerializedName("destination_user")
    var contact: Contact,
    var success: Boolean,
    var status: String
) : Parcelable