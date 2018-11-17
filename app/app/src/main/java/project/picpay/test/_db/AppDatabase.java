package project.picpay.test._db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import project.picpay.test._db.dao.CreditCardDao;
import project.picpay.test.creditcard.model.CreditCardModel;

/**
 * Created by Rodrigo Oliveira on 16/08/2018 for sac-digital-importacao.
 * ContactModel us rodrigooliveira.tecinfo@gmail.com
 */
@Database(entities = {CreditCardModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "credit_card_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract CreditCardDao creditCardDao();

}