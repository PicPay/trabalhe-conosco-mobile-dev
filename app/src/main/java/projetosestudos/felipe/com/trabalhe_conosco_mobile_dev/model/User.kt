package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("img")
    var img: String? = null

    @SerializedName("username")
    var username: String? = null

    constructor(id: Int?, name: String?, img: String?, username: String?) {
        this.id = id
        this.name = name
        this.img = img
        this.username = username
    }
}