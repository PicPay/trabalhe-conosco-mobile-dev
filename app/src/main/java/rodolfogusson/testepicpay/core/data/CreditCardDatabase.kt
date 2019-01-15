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
        @Volatile
        private var INSTANCE: CreditCardDatabase? = null

        fun getInstance(context: Context): CreditCardDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CreditCardDatabase::class.java,
            "creditCardDatabase.db")
            .build()
    }
}