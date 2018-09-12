package com.picpay.david.davidrockpicpay.entities

import com.picpay.david.davidrockpicpay.DavidRockPicPayApplication
import io.objectbox.Box
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor

//import io.realm.RealmObject
//import io.realm.annotations.PrimaryKey

@Entity
open class CreditCard {



    constructor(Id: Long, CardHolder: String, CardNumber: String?, Validity: String?, Cvv: Int?, Default: Boolean) {
        this.Id = Id
        this.CardHolder = CardHolder
        this.CardNumber = CardNumber
        this.Validity = Validity
        this.Cvv = Cvv
        this.Default = Default
    }

    constructor()

    @Id
    open var Id: Long = 0
    open var CardHolder: String? = null
    open var CardNumber: String? = null
    open var Validity: String? = null
    open var Cvv: Int? = null
    open var Default: Boolean = false

    //Database stuff

    fun getDefaultCard(): CreditCard? {
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()
        return box.all.firstOrNull()

    }

}