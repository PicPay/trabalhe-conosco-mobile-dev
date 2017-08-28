package com.tmontovaneli.picpaychallenge.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tmontovaneli on 23/08/17.
 */

public class ChallengeDAO extends SQLiteOpenHelper {

    public ChallengeDAO(Context context) {
        super(context, "PicPayChallenge", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Cartoes (nr_cartao CHAR(16) PRIMARY KEY, " +
                "nr_cvv char(3) NOT NULL, " +
                "dt_expiracao number);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
