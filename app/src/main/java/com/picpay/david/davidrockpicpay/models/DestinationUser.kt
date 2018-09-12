package com.picpay.david.davidrockpicpay.models

import com.google.gson.annotations.SerializedName

class DestinationUser {
    @SerializedName("id")
    var Id: Int = 0
    @SerializedName("name")
    var Name: String? = null
    @SerializedName("img")
    var Img: String? = null
    @SerializedName("username")
    var Username: String? = null
}