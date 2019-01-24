package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable

class ResponseTransaction(var transaction: Transaction? = null,
                          var status: Int = 0): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Transaction::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(transaction, flags)
        parcel.writeInt(status)
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