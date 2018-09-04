package com.picpay.david.davidrockpicpay.models


data class TransactionResponse(val Id: Int, val Timestamp: Int, val Value:String, val Success:Boolean, val Status: String, val DestinationUser: User){

}