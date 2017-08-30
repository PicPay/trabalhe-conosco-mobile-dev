package com.ghrc.picpay.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ghrc.picpay.contract.CreditCardContract;
import com.ghrc.picpay.contract.TransactionContract;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.model.Transaction;

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
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_CARD_NUMBER,card.getCard_number().replaceAll("\\s",""));
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_CVV,card.getCvv());
        contentValues.put(CreditCardContract.CreditCardEntry.COLUMN_EXPIRY,card.getExpiry_date());
        db.insert(CreditCardContract.CreditCardEntry.TABLE_NAME, null, contentValues);
    }

    public void inserTransaction(Transaction transaction) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TransactionContract.TransactionEntity.COLUMN_VALUE,transaction.getValue());
        contentValues.put(TransactionContract.TransactionEntity.COLUMN_USER_DESTINATION_ID,transaction.getDestination_user_id());
        contentValues.put(TransactionContract.TransactionEntity.COLUMN_DATE,transaction.getData());
        contentValues.put(TransactionContract.TransactionEntity.COLUMN_USER_IMAGE,transaction.getImg_user());
        contentValues.put(TransactionContract.TransactionEntity.COLUMN_USER_NAME,transaction.getUser_name());
        db.insert(TransactionContract.TransactionEntity.TABLE_NAME, null, contentValues);
    }

    public ArrayList<CreditCard> getCards(){
        ArrayList<CreditCard> list =  new ArrayList<>();
        String[] columns = {CreditCardContract.CreditCardEntry._ID,CreditCardContract.CreditCardEntry.COLUMN_CARD_NUMBER,
                CreditCardContract.CreditCardEntry.COLUMN_CVV, CreditCardContract.CreditCardEntry.COLUMN_EXPIRY};
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


    public void deleteCard(CreditCard c){
        db.delete(CreditCardContract.CreditCardEntry.TABLE_NAME, CreditCardContract.CreditCardEntry._ID + "=?", new String[]{c.getId()});
    }

    public ArrayList<Transaction> getHistory(){
        ArrayList<Transaction> list =  new ArrayList<>();
        String[] columns = {TransactionContract.TransactionEntity._ID,TransactionContract.TransactionEntity.COLUMN_DATE,
                TransactionContract.TransactionEntity.COLUMN_VALUE,TransactionContract.TransactionEntity.COLUMN_USER_DESTINATION_ID,TransactionContract.TransactionEntity.COLUMN_USER_IMAGE,
                TransactionContract.TransactionEntity.COLUMN_USER_NAME};
        Cursor cursor = db.query( TransactionContract.TransactionEntity.TABLE_NAME,columns,null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            list.add(new Transaction(
                    cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntity.COLUMN_USER_DESTINATION_ID)),
                    cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntity.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntity.COLUMN_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(TransactionContract.TransactionEntity.COLUMN_USER_IMAGE)),
                    cursor.getDouble(cursor.getColumnIndex(TransactionContract.TransactionEntity.COLUMN_VALUE))
            ));
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
            db.execSQL(TransactionContract.SQL_CREATE_TRANSACTION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
