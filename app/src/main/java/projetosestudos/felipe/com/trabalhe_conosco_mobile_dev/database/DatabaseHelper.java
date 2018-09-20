package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.AllTransactionsDao;
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.UserCardsDao;
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.dao.UserDataDao;
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.AllTransactions;
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserCards;
import projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model.UserData;

@Database(entities = {UserData.class, UserCards.class, AllTransactions.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract UserDataDao userDataDao();
    public abstract UserCardsDao userCardsDao();
    public abstract AllTransactionsDao allTransactionsDao();

    private static DatabaseHelper INSTANCE;

    public static DatabaseHelper getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, "database.helper")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

        private final UserDataDao mDao;
        private final UserCardsDao mDaoCards;
        private final AllTransactionsDao mDaoTransactions;

        public PopulateDBAsync(DatabaseHelper db) {
            mDao = db.userDataDao();
            mDaoCards = db.userCardsDao();
            mDaoTransactions = db.allTransactionsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

//            mDao.deleteData();

//            UserData word = new UserData(5.0);
//            mDao.insert(word);
            return null;
        }
    }

}
