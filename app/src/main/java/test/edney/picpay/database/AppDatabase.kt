package test.edney.picpay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.edney.picpay.BuildConfig

@Database(entities = [CardEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        private var instance: AppDatabase? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, BuildConfig.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
            instance
        }
    }
}