package br.com.picpay.picpay.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
abstract class PicpayDatabase: RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao
}