package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Transaction (@SerializedName("card_number")
                   var cardNumber: Int = 0,
                   var cvv: Int = 0,
                   var value: Float = 0F,
                   @SerializedName("expiry_date")
                   var expiryDate: String? = "",
                   @SerializedName("destination_user_id")
                   var destinationUserId: Int = 0): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cardNumber)
        parcel.writeInt(cvv)
        parcel.writeFloat(value)
        parcel.writeString(expiryDate)
        parcel.writeInt(destinationUserId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}