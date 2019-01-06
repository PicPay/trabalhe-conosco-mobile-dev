package com.michaeljordan.testemobilepicpay.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Card(
    @PrimaryKey
    var userId: Int,
    var number: String,
    var holderName: String,
    var expiryDate: String,
    var cvv: String
) : Parcelable