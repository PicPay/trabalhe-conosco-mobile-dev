package com.lacourt.picpay.picpaymobiledev.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lacourt.picpay.picpaymobiledev.Card;

import java.io.File;

/**
 * Created by igor on 18/08/2017.
 */

public class CardDAO extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String TABLE = "CARD";
    public static final String DATABASE = "PICPAY";
    private static final String[] COLS = {"num", "cvv", "date"};

    public CardDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABLE +
                "(num VARCHAR(16) PRIMARY KEY," +  " cvv INT NOT NULL," + " date TEXT" + ");";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void upgradeTable(){
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        getWritableDatabase().execSQL(sql);
        onCreate(getWritableDatabase());
    }

    public void insert(Card card){
        ContentValues values = new ContentValues();
        values.put("num", card.getCardNumber());
        values.put("cvv", card.getCvv());
        values.put("date", card.getExpiryDate());
        getWritableDatabase().insert(TABLE, null, values);
    }

    public String getCardValue(String value){
        Cursor cursor = getWritableDatabase().rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + TABLE + "'", null);
        if(cursor.getCount() != 0){
            Log.d("dbtest", "table count: " + cursor.getCount());
            cursor = getWritableDatabase().query(TABLE, COLS,  null, null, null, null, null);
            if(cursor.getCount() != 0){
                Log.d("dbtest", "colum count: " + cursor.getCount());
                cursor.moveToNext();
                Log.d("dbtest", "returns: "+ cursor.getInt(0));

                switch (value){
                    case "num": return cursor.getString(0);
                    case "cvv": return Integer.toString(cursor.getInt(1));
                    case "date": return cursor.getString(2);
                }
            }
        }
        return "null";
    }

    public String getRegisteredCard(){
        Cursor cursor = getWritableDatabase().rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + TABLE + "'", null);
        if(cursor.getCount() != 0){
            Log.d("dbtest", "table count: " + cursor.getCount());
            cursor = getWritableDatabase().query(TABLE, COLS,  null, null, null, null, null);
            if(cursor.getCount() != 0){
                Log.d("dbtest", "colum count: " + cursor.getCount());
                cursor.moveToNext();
                Log.d("dbtest", "returns: "+ cursor.getInt(0));
                return cursor.getString(0);
            }
        }
        return "null";
    }


}
