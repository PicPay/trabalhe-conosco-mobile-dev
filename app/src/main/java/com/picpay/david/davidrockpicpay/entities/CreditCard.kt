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
        return box.query().equal(CreditCard_.Default, true).build().findFirst()
    }

    fun setDefaultCard(card: CreditCard) {
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()

        //Remover atributo de cartão padrão de todos antes de settar um cartão como padrão
        var list = box.all
        list.forEach {
            it.Default = false
            box.put(it)
        }

        card.Default = true
        box.put(card)

        var lista = box.all

    }

    fun getAll(): List<CreditCard> {
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()

        return box.all
    }

    fun removeCreditCard(id: Long){
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()

        box.remove(id)
    }

    fun getById(id: Long): CreditCard? {
        val box: Box<CreditCard> = DavidRockPicPayApplication.boxStore.boxFor()

        return box.query().equal(CreditCard_.Id, id).build().findFirst()
    }
}