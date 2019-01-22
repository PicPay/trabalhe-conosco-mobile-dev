package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Payment (@SerializedName("card_number")
               var cardNumber: String? = "",
               var cvv: Int = 0,
               var value: Float = 0F,
               @SerializedName("expiry_date")
               var expiryDate: String? = "",
               @SerializedName("destination_user_id")
               var destinationUserId: Int = 0): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardNumber)
        parcel.writeInt(cvv)
        parcel.writeFloat(value)
        parcel.writeString(expiryDate)
        parcel.writeInt(destinationUserId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Payment> {
        override fun createFromParcel(parcel: Parcel): Payment {
            return Payment(parcel)
        }

        override fun newArray(size: Int): Array<Payment?> {
            return arrayOfNulls(size)
        }
    }
}