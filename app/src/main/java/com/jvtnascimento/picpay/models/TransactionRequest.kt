package com.jvtnascimento.picpay.models

data class TransactionRequest (
    var value: Float,
    var card_number: String,
    var expiry_date: String,
    var cvv: Int,
    var destination_user_id: Int
)