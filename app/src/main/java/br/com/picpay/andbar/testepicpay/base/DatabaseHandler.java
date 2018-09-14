package br.com.picpay.andbar.testepicpay.base;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.picpay.andbar.testepicpay.CartaoUsuario;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PicPay";

    // Contacts table name
    public static final String TABLE_CARDS = "Cartoes";


    // Contacts Table Columns names
    private static final String ID_CARTAO = "CartaoId";
    private static final String NUMERO_CARTAO = "CartaoNumero";
    private static final String CVV = "CartaoCvv";
    private static final String VALIDADE = "CartaoValidade";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + ID_CARTAO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NUMERO_CARTAO + " TEXT," + CVV + " TEXT," + VALIDADE
                + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);


        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public boolean addCartao(CartaoUsuario card, String Table)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(NUMERO_CARTAO, card.NumeroCartao); // Contact Name
            values.put(CVV, card.CodigoValidacao); // Contact Phone
            values.put(VALIDADE, card.ValidadeCartao);

            // Inserting Row
            db.insert(Table, null, values);

            db.close(); // Closing database connection
            return true;
        }
        catch (Exception erro)
        {
            return false;
        }
    }

    // Getting single contact
    CartaoUsuario getCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARDS, new String[] { ID_CARTAO,
                        NUMERO_CARTAO, CVV, VALIDADE, }, ID_CARTAO
                        + "=?", new String[] { String.valueOf(id) }, null, null, null,
                null);
        if (cursor != null)
            cursor.moveToFirst();

        CartaoUsuario card = new CartaoUsuario();

        card.Id = Integer.parseInt(cursor.getString(0));
        card.NumeroCartao = cursor.getString(1);
        card.CodigoValidacao = cursor.getString(2);
        card.ValidadeCartao = cursor.getString(3);

        return card;
    }




    public List<CartaoUsuario> getAllCards(String Table) {
        List<CartaoUsuario> cardList = new ArrayList<CartaoUsuario>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table;
        // ORDER BY "+ KEY_ID +" DESC
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Log.d("db", cursor.toString());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartaoUsuario cartao = new CartaoUsuario();
                cartao.Id = (Integer.parseInt(cursor.getString(0)));
                cartao.NumeroCartao = (cursor.getString(1));
                cartao.CodigoValidacao = (cursor.getString(2));
                cartao.ValidadeCartao = (cursor.getString(3));
                // Adding contact to list
                cardList.add(cartao);
            } while (cursor.moveToNext());
        }

        // return contact list
        return cardList;
    }


    public CartaoUsuario getCard(String Table, String CardNumber)
    {
        CartaoUsuario cardSelected = new CartaoUsuario();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table + " WHERE " + NUMERO_CARTAO + " = '" + CardNumber + "'";
        // ORDER BY "+ KEY_ID +" DESC
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Log.d("db", cursor.toString());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartaoUsuario cartao = new CartaoUsuario();
                cartao.Id = (Integer.parseInt(cursor.getString(0)));
                cartao.NumeroCartao = (cursor.getString(1));
                cartao.CodigoValidacao = (cursor.getString(2));
                cartao.ValidadeCartao = (cursor.getString(3));
                // Adding contact to list
                cardSelected = cartao;
                break;
            } while (cursor.moveToNext());
        }

        // return contact list
        return cardSelected;
    }




    // // Updating single contact
    // public int updateContact(Contact contact) {
    // SQLiteDatabase db = this.getWritableDatabase();
    //
    // ContentValues values = new ContentValues();
    // values.put(KEY_NAME, contact.getName());
    // values.put(KEY_PH_NO, contact.getPhoneNumber());
    //
    // // updating row
    // return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
    // new String[] { String.valueOf(contact.getID()) });
    // }



}
