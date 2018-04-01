package br.com.vdsantana.picpaytest.creditcard

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by vd_sa on 30/03/2018.
 */
class CreditCard : Serializable {

    @SerializedName("card_number")
    var cardNumber: String = ""


    @SerializedName("expiry_date")
    var expiryDate: String = ""
}