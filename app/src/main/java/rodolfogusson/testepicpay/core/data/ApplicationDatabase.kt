package rodolfogusson.testepicpay.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rodolfogusson.testepicpay.core.logic.SingletonHolder
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCard
import rodolfogusson.testepicpay.sendpayment.model.creditcard.CreditCardDao

@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun creditCardDao(): CreditCardDao

    companion object : SingletonHolder<ApplicationDatabase, Context>({ context ->
        Room.databaseBuilder(
            context.applicationContext,
            ApplicationDatabase::class.java,
            "database.db")
            .build()
    })
}