package com.example.igor.picpayandroidx.Model

data class Transaction(
    val id: Int,
    val timestamp: Int,
    val value: Int,
    val destination_user: DestinationUser,
    val success: Boolean,
    val status: String
)