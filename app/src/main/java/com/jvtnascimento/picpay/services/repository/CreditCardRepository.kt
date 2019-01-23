package com.jvtnascimento.picpay.services.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.jvtnascimento.picpay.models.CreditCard
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseOpt
import org.jetbrains.anko.db.select

class CreditCardRepository(database: SQLiteDatabase){

    private var database: SQLiteDatabase = database

    fun findOne(): CreditCard? {

        var ret: CreditCard? = null

        this.database.select("CreditCard", "id", "number", "name", "expirationDate", "cvv")
            .whereArgs("(id = {creditCardId})",
                "creditCardId" to 1)
            .exec {
                ret = parseOpt(classParser())
            }

        return ret
    }

    fun create(creditCard: CreditCard) {
        val values = ContentValues()
        values.put("id", 1)
        values.put("number", creditCard.number)
        values.put("name", creditCard.name)
        values.put("expirationDate", creditCard.expirationDate)
        values.put("cvv", creditCard.cvv)

        this.database.insert("CreditCard", null, values)
    }

    fun update(creditCard: CreditCard) {
        val values = ContentValues()
        values.put("id", 1)
        values.put("number", creditCard.number)
        values.put("name", creditCard.name)
        values.put("expirationDate", creditCard.expirationDate)
        values.put("cvv", creditCard.cvv)

        this.database.update("CreditCard", values, "id = ?", arrayOf(creditCard.id.toString()))
    }
}