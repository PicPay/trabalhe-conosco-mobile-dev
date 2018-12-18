package br.com.kassianoresende.picpay.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(CreditCard::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao
}