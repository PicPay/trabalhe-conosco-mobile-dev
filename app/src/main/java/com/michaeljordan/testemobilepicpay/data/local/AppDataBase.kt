package com.michaeljordan.testemobilepicpay.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.michaeljordan.testemobilepicpay.data.local.dao.CardDao
import com.michaeljordan.testemobilepicpay.model.Card

@Database(entities = arrayOf(Card::class), version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        private val DATABASE_NAME = "testepicpay"
        private lateinit var instance: AppDataBase

        fun getAppDatabase(context: Context): AppDataBase {
            synchronized(AppDataBase::class.java) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATABASE_NAME
                )
                    .build()
            }
            return instance
        }
    }
}
