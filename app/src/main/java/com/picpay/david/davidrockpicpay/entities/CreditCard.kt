package com.picpay.david.davidrockpicpay.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CreditCard : RealmObject() {

    @PrimaryKey
    open var Id: Int = 0
    open var CardNumber: String? = null
    open var ValidMonth: String? = null
    open var ValidYear: String? = null
    open var Cvv: Int? = null

}