package com.jvtnascimento.picpay.models

data class Transaction (
    var id: Int,
    var value: Double,
    var success: Boolean,
    var status: String,
    var destination_user: User,
    var timestamp: String
)