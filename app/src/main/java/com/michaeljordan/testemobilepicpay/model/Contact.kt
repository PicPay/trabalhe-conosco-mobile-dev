package com.michaeljordan.testemobilepicpay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var id: Int,
    var name: String,
    @SerializedName("img")
    var image: String,
    var username: String
) : Parcelable