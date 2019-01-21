package com.jvtnascimento.picpay.models

import java.io.Serializable

class CreditCard : Serializable {
    var id: Int = 0
    var number: String = ""
    var name: String = ""
    var expirationDate: String = ""
    var cvv: Int = 0

    var firstFourNumbers: String = "0000"
        get() = this.number.take(4)

    constructor()

    constructor(id: Int, number: String, name: String, expirationDate: String, cvv: Int) {
        this.id = id
        this.number = number
        this.name = name
        this.expirationDate = expirationDate
        this.cvv = cvv
    }
}