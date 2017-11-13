package br.com.devmarques.picpaytestes.DAO.HistoryTransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roger on 10/11/2017.
 */

public class BancoTransacoes extends SQLiteOpenHelper {

    private static final String NOMEBANCO = "Transacoes";
    private static final int VERSAOBANCO = 1;
    private static final String TABLECARD = "CREATE TABLE Transacoes (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "transacaoNum TEXT,valor TEXT, status TEXT, foto TEXT, user TEXT," +
            " cardNumberandName TEXT, nome TEXT, aprovada TEXT );";

    public BancoTransacoes(Context context) {
        super(context, NOMEBANCO, null, VERSAOBANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLECARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Transacoes;");
    }
}