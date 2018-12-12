package test.edney.picpay.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DestinationUserModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("img")
    @Expose
    var img: String? = null
    @SerializedName("username")
    @Expose
    var username: String? = null
}