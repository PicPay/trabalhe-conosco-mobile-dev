package com.v1pi.picpay_teste.Domains

import com.google.gson.annotations.Expose

data class User(@Expose val id: Int,
                @Expose val name: String = "",
                @Expose val img: String = "",
                @Expose val username : String = "") {
    override fun equals(other: Any?): Boolean {
        if(other is User) {
            return other.id == this.id && this.name == other.name && this.img == other.img && this.username == other.username
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}