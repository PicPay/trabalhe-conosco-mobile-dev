package br.com.devmarques.picpaytestes.DAO.CartoesCad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by igornobre on 17/11/16.
 */

public class BancoCardDAO extends SQLiteOpenHelper {

    private static final String NOMEBANCO = "Cartoes";
    private static final int VERSAOBANCO = 1;
    private static final String TABLECARD = "CREATE TABLE Cartoes (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nomeCard TEXT,cardNumber TEXT, cvv TEXT, expiryDate TEXT);";

    public BancoCardDAO(Context context) {
        super(context, NOMEBANCO, null, VERSAOBANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLECARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Cartao;");
    }
}





































