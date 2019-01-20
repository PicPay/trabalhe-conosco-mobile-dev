package com.jvtnascimento.picpay.models

data class Transaction (
    var card_number: String,
    var cvv: Int,
    var value: Float,
    var expiry_date: String,
    var destination_user_id: String
)