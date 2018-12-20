package br.com.kassianoresende.picpay.model

import com.google.gson.annotations.SerializedName


class PayUserTransaction(

        @SerializedName("card_number")
        val cardNumber:String,

        val cvv:Int,
        val value:Double,

        @SerializedName("expiry_date")
        val expiryDate:String,

        @SerializedName("destination_user_id")
        val destinationUserId:Int
    )
