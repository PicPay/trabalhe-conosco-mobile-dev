package com.jvtnascimento.picpay.services.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.jvtnascimento.picpay.services.Constants
import org.jetbrains.anko.db.*

class DBOpenHelper(context: Context):
    ManagedSQLiteOpenHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION) {

    companion object {
        private var instance: DBOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBOpenHelper {
            if (instance == null) {
                instance = DBOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("CreditCard", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "number" to TEXT,
            "name" to TEXT,
            "expirationDate" to TEXT,
            "cvv" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("CreditCard", true)
    }
}