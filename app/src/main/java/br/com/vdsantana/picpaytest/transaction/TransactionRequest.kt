package br.com.vdsantana.picpaytest.transaction

import com.google.gson.annotations.SerializedName

/**
 * Created by vd_sa on 31/03/2018.
 */
data class TransactionRequest(@SerializedName("card_number") val cardNumber: String?, val cvv: Int?, val value: Double?, @SerializedName("expiry_date") val expiryDate: String?, @SerializedName("destination_user_id") val destinationUserId: Int?)