package com.michaeljordan.testemobilepicpay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionResponse(
    @SerializedName("transaction")
    var transaction: Transaction
) : Parcelable