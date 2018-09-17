package com.picpay.david.davidrockpicpay.models

import com.google.gson.annotations.SerializedName

open class User {
    @SerializedName("id")
    var Id: Int? = 0
    @SerializedName("name")
    var Name: String? = null
    @SerializedName("img")
    var Img: String? = null
    @SerializedName("username")
    var UserName: String? = null

    constructor(Id: Int?, Name: String?, Img: String?, UserName: String?) {
        this.Id = Id
        this.Name = Name
        this.Img = Img
        this.UserName = UserName
    }

    constructor()


}