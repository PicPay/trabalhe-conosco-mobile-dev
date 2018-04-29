package com.v1pi.picpay_teste.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.v1pi.picpay_teste.Dao.CreditCardDao
import com.v1pi.picpay_teste.Domains.CreditCard

@Database(entities = [CreditCard::class], version = 2)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun creditCardDao() : CreditCardDao

    companion object {
        private var INSTANCE : DatabaseManager? = null

        fun getInstance(context: Context) : DatabaseManager? {
            if(INSTANCE == null) {
                synchronized(DatabaseManager::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DatabaseManager::class.java, "app_database").build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}