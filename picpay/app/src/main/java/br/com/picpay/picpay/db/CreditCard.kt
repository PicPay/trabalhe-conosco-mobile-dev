package br.com.picpay.picpay.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "credit_card")
data class CreditCard (@PrimaryKey
                       var cardNumber: String = "",
                       var cvv: Int = 0,
                       var cardholderName: String = "",
                       var expiryDate: String = "")