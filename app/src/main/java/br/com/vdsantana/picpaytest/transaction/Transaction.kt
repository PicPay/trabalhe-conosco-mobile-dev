package br.com.vdsantana.picpaytest.transaction

import br.com.vdsantana.picpaytest.users.User
import com.google.gson.annotations.SerializedName

/**
 * Created by vd_sa on 31/03/2018.
 */
data class Transaction(
        @SerializedName("destination_user")
        val destinationUser: User,
        val id: Number,
        val status: String,
        val success: Boolean,
        val timestamp: Number,
        val value: Number
)