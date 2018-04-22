package com.v1pi.picpay_teste.Domains

data class User(val id: Int, val name: String = "", val img: String = "", val username : String = "") {
    override fun toString(): String {
        return "Nome: $name"
    }

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