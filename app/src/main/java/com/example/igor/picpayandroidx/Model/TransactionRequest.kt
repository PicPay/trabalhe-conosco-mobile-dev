package com.example.igor.picpayandroidx.Model

import android.os.Parcel
import android.os.Parcelable

data class TransactionRequest (var card_number: String = "",
                               var cvv: Int = 0,
                               var value: Float = 0f,
                               var expiry_date: String = "",
                               var destination_user_id: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(card_number)
        parcel.writeInt(cvv)
        parcel.writeFloat(value)
        parcel.writeString(expiry_date)
        parcel.writeInt(destination_user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionRequest> {
        override fun createFromParcel(parcel: Parcel): TransactionRequest {
            return TransactionRequest(parcel)
        }

        override fun newArray(size: Int): Array<TransactionRequest?> {
            return arrayOfNulls(size)
        }
    }


}