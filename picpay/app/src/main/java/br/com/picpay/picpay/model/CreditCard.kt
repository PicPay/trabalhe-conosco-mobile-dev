package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable


class CreditCard (var cardNumber: Int = 0,
                  var cvv: Int = 0,
                  var cardholderName: String? = "",
                  var expiryDate: String? = ""): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cardNumber)
        parcel.writeInt(cvv)
        parcel.writeString(cardholderName)
        parcel.writeString(expiryDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreditCard> {
        override fun createFromParcel(parcel: Parcel): CreditCard {
            return CreditCard(parcel)
        }

        override fun newArray(size: Int): Array<CreditCard?> {
            return arrayOfNulls(size)
        }
    }
}