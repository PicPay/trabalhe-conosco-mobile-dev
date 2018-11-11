package test.edney.picpay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    companion object {
        private var instance: AppDatabase? = null

        fun get(context: Context) : AppDatabase? {
            if(instance == null){
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "weather.db"
                    ).build()
                }
            }

            return instance
        }
    }
}