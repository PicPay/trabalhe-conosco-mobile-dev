package rodolfogusson.testepicpay.core.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCardDao

@Database(entities = [CreditCard::class], version = 1)
abstract class CreditCardDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    companion object {
        private lateinit var INSTANCE: CreditCardDatabase

        fun getInstance(context: Context): CreditCardDatabase {
            synchronized(CreditCardDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CreditCardDatabase::class.java,
                    "creditCardDatabase.db"
                )
                    .build()
            }
            return INSTANCE
        }
    }
}