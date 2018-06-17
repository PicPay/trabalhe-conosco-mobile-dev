package com.example.eduardo.demoapppagamento.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.eduardo.demoapppagamento.data.Card;

/**
 * The Room Database that contains the Cards table.
 */
@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class CardsDatabase extends RoomDatabase {

    private static CardsDatabase INSTANCE;
    private static String DATABASE_NAME = "Cards.db";
    private static final Object sLock = new Object();

    public abstract CardsDao cardsDao();

    public static CardsDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (sLock) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        CardsDatabase.class, CardsDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;

    }
}
