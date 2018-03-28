package mobiledev.erickgomes.picpayapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mobiledev.erickgomes.picpayapp.models.CreditCard;

/**
 * Created by erickgomes on 24/03/2018.
 */

@Database(entities = {CreditCard.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CreditCardDao creditCardDao();
}
