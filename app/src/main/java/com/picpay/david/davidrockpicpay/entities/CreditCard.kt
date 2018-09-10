package com.picpay.david.davidrockpicpay.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

//import io.realm.RealmObject
//import io.realm.annotations.PrimaryKey

@Entity
open class CreditCard  {

    @Id open var Id: Long = 0
    open var CardNumber: String? = null
    open var ValidMonth: String? = null
    open var ValidYear: String? = null
    open var Cvv: Int? = null
    open var Default: Boolean = false

}