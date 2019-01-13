package picpayteste.devmarques.com.picpay_teste.dao.cartao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoCardDao extends SQLiteOpenHelper {

    private static final String NOMEBANCO = "Cartoes";
    private static final int VERSAOBANCO = 1;
    private static final String TABLECARD = "CREATE TABLE Cartoes (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +"nomeCard TEXT,"
            +"cardNumber TEXT,"
            +"cvv TEXT,"
            +"expiryDate TEXT);";

    public BancoCardDao(Context context) {
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

