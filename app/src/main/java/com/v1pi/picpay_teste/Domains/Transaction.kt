package com.v1pi.picpay_teste.Domains

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Transaction(@Expose val card_number : String,
                       @Expose val cvv : Int,
                       @Expose(serialize = false) val value : Float,
                       @Expose val expiry_date : String,
                       @Expose(serialize = false) var destination_user : User,
                       @Expose(serialize = false) val success: Boolean? = null,
                       @Expose(serialize = false) val status : String? = null,
                       @Expose(serialize = false) val timestamp : Long? = null,
                       @Expose(serialize = false) val id : Int? = null,
                       @Expose(deserialize = false) val destination_user_id : Int = destination_user.id) {

    override fun equals(other: Any?): Boolean {
        if(other is Transaction) {
            return card_number == other.card_number && cvv == other.cvv && value == other.value &&
                    expiry_date == other.expiry_date && destination_user == other.destination_user
        }
        return false
    }

    override fun hashCode(): Int {
        var result = card_number.hashCode()
        result = 31 * result + cvv
        result = 31 * result + value.hashCode()
        result = 31 * result + expiry_date.hashCode()
        result = 31 * result + destination_user.hashCode()
        return result
    }


}