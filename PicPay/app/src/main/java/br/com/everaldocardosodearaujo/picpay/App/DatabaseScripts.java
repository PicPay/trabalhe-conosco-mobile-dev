package br.com.everaldocardosodearaujo.picpay.App;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static br.com.everaldocardosodearaujo.picpay.App.Session.DB_NAME;
import static br.com.everaldocardosodearaujo.picpay.App.Session.DB_VERSION;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class DatabaseScripts extends SQLiteOpenHelper {

    public DatabaseScripts(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL;

        SQL =  " CREATE TABLE USERS( ";
        SQL += "    ID INTEGER PRIMARY KEY , ";
        SQL += "    NAME TEXT NOT NULL , ";
        SQL += "    USERNAME TEXT NOT NULL , ";
        SQL += "    IMG TEXT NULL  ";
        SQL += " ); ";

        db.execSQL(SQL);

        SQL =  " CREATE TABLE CREDIT_CARD( ";
        SQL += "    ID INTEGER PRIMARY KEY AUTOINCREMENT , ";
        SQL += "    FLAG TEXT NOT NULL , ";
        SQL += "    NAME TEXT NOT NULL , ";
        SQL += "    NUMBERCARD TEXT NULL  ";
        SQL += "    VALIDITY TEXT NULL  ";
        SQL += "    CCV TEXT NULL  ";
        SQL += " ); ";

        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL;

        SQL  = " DROP TABLE USERS; ";
        db.execSQL(SQL);

        SQL  = " DROP TABLE CREDIT_CARD; ";
        db.execSQL(SQL);
    }
}
