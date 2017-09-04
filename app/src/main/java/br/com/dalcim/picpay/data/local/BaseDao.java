package br.com.dalcim.picpay.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.dalcim.picpay.R;

public abstract class BaseDao extends SQLiteOpenHelper {
    public BaseDao(Context context) {
        super(context, context.getResources().getString(R.string.db_name), null,  context.getResources().getInteger(R.integer.db_version));
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DB.CREDIT_CARD.createQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
