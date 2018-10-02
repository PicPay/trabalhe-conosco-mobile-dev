package com.example.igor.roompersistence.local

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.igor.picpayandroidx.Model.Card
import com.example.igor.roompersistence.local.CardDataBase.Companion.DATABASE_VERSION

private var INSTANCE: CardDataBase? = null

@Database(entities = arrayOf(Card::class), version = DATABASE_VERSION)
abstract class  CardDataBase: RoomDatabase() {

    abstract fun cardDAO() : CardDAO

    companion object {

        const val DATABASE_VERSION = 2 // const makes the variable value known at compile time (used for anotations)
        val DATABASE_NAME = "card_database"

        fun getDatabase(context: Context): CardDataBase? {
            if (INSTANCE == null) {
                synchronized(CardDataBase::class.java) {
                    if (INSTANCE == null) {
                        // Create database here
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                CardDataBase::class.java, DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }

    }

}