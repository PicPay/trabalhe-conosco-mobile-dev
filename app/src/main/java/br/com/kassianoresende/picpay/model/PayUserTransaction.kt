package br.com.kassianoresende.picpay.model

import com.squareup.moshi.Json

class PayUserTransaction(

        @Json(name = "card_number")
        val cardNumber:String,

        val cvv:Int,
        val value:Double,

        @Json(name = "expiry_date")
        val expiryDate:String,

        @Json(name = "destination_user_id")
        val destinationUserId:Int
    )
