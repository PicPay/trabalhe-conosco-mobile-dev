package br.com.kassianoresende.picpay.di.module

import android.app.Application
import android.arch.persistence.room.Room
import br.com.kassianoresende.picpay.model.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable


@Module
class DatabaseModule(val app:Application){


    @Provides
    @Reusable
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "picpay.db")
            .build()
    }

}