package rodolfogusson.testepicpay.core.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import rodolfogusson.testepicpay.core.logic.SingletonHolder
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.payment.model.creditcard.CreditCardDao

@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CreditCardDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    companion object : SingletonHolder<CreditCardDatabase, Context>({ context ->
        Room.databaseBuilder(
            context.applicationContext,
            CreditCardDatabase::class.java,
            "creditCardDatabase.db")
            .build()
    })
}