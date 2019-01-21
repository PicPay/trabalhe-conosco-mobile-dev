package com.jvtnascimento.picpay.models

import java.io.Serializable

data class User(
    var id: Int,
    var name: String,
    var username: String,
    var img: String
): Serializable