package com.ghrc.picpay.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ghrc.picpay.contract.CreditCardContract;
import com.ghrc.picpay.model.CreditCard;

import java.util.ArrayList;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class BD {
    private SQLiteDatabase db;
    public BD(Context c) {
        BDCore aux = BDCore.getInstance(c);
        db = aux.getWritableDatabase();
    }
    public void insertCreditCard(CreditCard card) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_CARD_NUMBER,card.getCard_number());
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_CVV,card.getCvv());
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_EXPIRY,card.getExpiry_date());
        db.insert(CreditCardContract.CreditCardEntry.TABLE_NAME, null, contentValues);
    }

    public ArrayList<CreditCard> getCards(){
        ArrayList<CreditCard> list =  new ArrayList<>();
        String[] columns = {CreditCardContract.CreditCardEntry._ID,CreditCardContract.CreditCardEntry.COLUMN_CARD_NUMBER,CreditCardContract.CreditCardEntry.COLUMN_CVV, CreditCardContract.CreditCardEntry.COLUMN_EXPIRY};
        Cursor cursor = db.query(CreditCardContract.CreditCardEntry.TABLE_NAME,columns,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            list.add(new CreditCard(cursor.getString(cursor.getColumnIndex(CreditCardContract.CreditCardEntry._ID))
                    , cursor.getString(cursor.getColumnIndex(CreditCardContract.CreditCardEntry.COLUMN_CARD_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(CreditCardContract.CreditCardEntry.COLUMN_CVV)),
                    cursor.getString(cursor.getColumnIndex(CreditCardContract.CreditCardEntry.COLUMN_EXPIRY))));
        }
        cursor.close();
        return list;
    }
    private static class BDCore extends SQLiteOpenHelper {
        private static final String TAG = "BD";
        private static final String NOMEDB = "bd";
        private  static final int VERSAO_DB = 1;

        private static BDCore mInstance;
        BDCore(Context context)
        {
            super(context,NOMEDB,null,VERSAO_DB);
        }
        static synchronized BDCore getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new BDCore(context.getApplicationContext());
            }
            return mInstance;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreditCardContract.SQL_CREATE_CREDIT_CARD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
