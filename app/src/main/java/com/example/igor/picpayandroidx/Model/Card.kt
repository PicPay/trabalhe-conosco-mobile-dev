package com.example.igor.picpayandroidx.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity(tableName = "cards")
class Card() : Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @NonNull
    @ColumnInfo(name = "number")
    var number: String = ""

    @ColumnInfo(name = "cvv")
    var cvv: Int = 0

    @ColumnInfo(name = "expdate")
    var expdate: String = "00/00"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        number = parcel.readString()
        cvv = parcel.readValue(Int::class.java.classLoader) as Int
        expdate = parcel.readString()
    }

    override fun toString(): String {
        return StringBuilder("xxxx xxxx xxxx ${number.subSequence(12, 16)}")
                .append("\nValidade: $expdate").toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(number)
        parcel.writeValue(cvv)
        parcel.writeString(expdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Card> {
        override fun createFromParcel(parcel: Parcel): Card {
            return Card(parcel)
        }

        override fun newArray(size: Int): Array<Card?> {
            return arrayOfNulls(size)
        }
    }
}