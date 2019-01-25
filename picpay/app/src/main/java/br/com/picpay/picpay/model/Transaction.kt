package br.com.picpay.picpay.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Transaction(var id: Int = 0,
                  var timestamp: Long = 0,
                  var value: Float = 0F,
                  @SerializedName("destination_user")
                  var destinationUser: User? = null,
                  var success: Boolean = false,
                  var status: String? = "",
                  var cardCompany: String? = "",
                  var cardLastNumbers: String? = ""): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(timestamp)
        parcel.writeFloat(value)
        parcel.writeParcelable(destinationUser, flags)
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeString(status)
        parcel.writeString(cardCompany)
        parcel.writeString(cardLastNumbers)
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