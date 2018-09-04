package com.picpay.david.davidrockpicpay.models


data class TransactionModel(val CardNumber: String, val Cvv: Int, val Value:Double, val ExpiryDate: String, val DestinationUserId: Int){

}