package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable

class ResponseTransaction(var transaction: Transaction? = null,
                          var success: Boolean = false,
                          var status: String? = ""): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Transaction::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(transaction, flags)
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseTransaction> {
        override fun createFromParcel(parcel: Parcel): ResponseTransaction {
            return ResponseTransaction(parcel)
        }

        override fun newArray(size: Int): Array<ResponseTransaction?> {
            return arrayOfNulls(size)
        }
    }
}