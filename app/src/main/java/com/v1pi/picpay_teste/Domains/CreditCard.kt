package com.v1pi.picpay_teste.Domains

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class CreditCard(@PrimaryKey(autoGenerate = true) val uid: Int,
                      @ColumnInfo(name = "number") val number: String,
                      @ColumnInfo(name = "cvv") val cvv: Int,
                      @ColumnInfo(name = "expiry_date") val expiry_date: String) {
    override fun equals(other: Any?): Boolean {
        if(other is CreditCard) {
            return other.uid == this.uid && other.number == this.number && other.cvv == this.cvv && other.expiry_date == this.expiry_date
        }
        return false
    }

    override fun hashCode(): Int {
        var result = uid
        result = 31 * result + number.hashCode()
        result = 31 * result + cvv
        result = 31 * result + expiry_date.hashCode()
        return result
    }
}