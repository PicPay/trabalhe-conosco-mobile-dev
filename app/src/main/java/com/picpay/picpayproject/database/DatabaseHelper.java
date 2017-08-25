package com.picpay.picpayproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.picpay.picpayproject.model.Cartao;
import com.picpay.picpayproject.model.Usuario;

import java.util.ArrayList;

/**
 * Created by felip on 19/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PicPayDatabase";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_DIRETORIO = "/data/data/com.picpay.picpayproject/databases/PicPayDatabase.db";

    protected static final String TABLE_USUARIO_COLUNA1 = "ID";
    protected static final String TABLE_USUARIO_COLUNA2 = "NOME";
    protected static final String TABLE_USUARIO_COLUNA3 = "EMAIL";
    protected static final String TABLE_USUARIO_COLUNA4 = "USERNAME";
    protected static final String TABLE_USUARIO_COLUNA5 = "SENHA";

    protected static final String TABLE_CARTAO_COLUNA1 = "CARTAOID";
    protected static final String TABLE_CARTAO_COLUNA2 = "USERID";
    protected static final String TABLE_CARTAO_COLUNA3 = "NUMERO";
    protected static final String TABLE_CARTAO_COLUNA4 = "CVV";
    protected static final String TABLE_CARTAO_COLUNA5 = "VALIDADE";

    protected static final String TABLE_NAME_USUARIOS = "USUARIOS";
    protected static final String TABLE_NAME_CARTAO = "CARTOES";

    protected static final String SQL_TABLE_CREATE_USUARIOS = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME_USUARIOS + "(" +
            TABLE_USUARIO_COLUNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_USUARIO_COLUNA2 + " VARCHAR," +
            TABLE_USUARIO_COLUNA3 + " VARCHAR," +
            TABLE_USUARIO_COLUNA4 + " VARCHAR," +
            TABLE_USUARIO_COLUNA5 + " VARCHAR)";

    protected static final String SQL_TABLE_CREATE_CARTOES = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME_CARTAO + "(" +
            TABLE_CARTAO_COLUNA1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_CARTAO_COLUNA2 + " INTEGER," +
            TABLE_CARTAO_COLUNA3 + " VARCHAR," +
            TABLE_CARTAO_COLUNA4 + " VARCHAR," +
            TABLE_CARTAO_COLUNA5 + " VARCHAR)";

    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_CREATE_USUARIOS);
        db.execSQL(SQL_TABLE_CREATE_CARTOES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserirUsuario(String nome, String email, String username, String senha) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_USUARIO_COLUNA2, nome);
        values.put(TABLE_USUARIO_COLUNA3, email);
        values.put(TABLE_USUARIO_COLUNA4, username);
        values.put(TABLE_USUARIO_COLUNA5, senha);

        db.insert(TABLE_NAME_USUARIOS, null, values);
        db.close();
    }

    public void inserirCartao(String userId, String numero, String cvv, String validade) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_CARTAO_COLUNA2, " " + userId);
        values.put(TABLE_CARTAO_COLUNA3, " " + numero);
        values.put(TABLE_CARTAO_COLUNA4, " " + cvv);
        values.put(TABLE_CARTAO_COLUNA5, " " + validade);

        db.insert(TABLE_NAME_CARTAO, null, values);
        db.close();
    }

    public ArrayList<Cartao> consultarCartao(String idUser) {

        ArrayList<Cartao> cartaoList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projecao = {TABLE_CARTAO_COLUNA1, TABLE_CARTAO_COLUNA2, TABLE_CARTAO_COLUNA3,
                TABLE_CARTAO_COLUNA4, TABLE_CARTAO_COLUNA5};
        String selection = TABLE_CARTAO_COLUNA2 + "=?";
        String[] selectionArgs = {" " + idUser};

        Cursor cursor = db.query(
                TABLE_NAME_CARTAO,
                projecao,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int indiceColunaId = cursor.getColumnIndex(TABLE_CARTAO_COLUNA1);
        int indiceColunaUserId = cursor.getColumnIndex(TABLE_CARTAO_COLUNA2);
        int indiceColunaNumero = cursor.getColumnIndex(TABLE_CARTAO_COLUNA3);
        int indiceColunaCvv = cursor.getColumnIndex(TABLE_CARTAO_COLUNA4);
        int indiceColunaValidade = cursor.getColumnIndex(TABLE_CARTAO_COLUNA5);

        cursor.moveToFirst();

        if (cursor.getCount() != 0) {

            do {
                Cartao cartao = new Cartao();
                cartao.setIdCartao(cursor.getString(indiceColunaId));
                cartao.setIdUsuario(cursor.getString(indiceColunaUserId));
                cartao.setNumero(cursor.getString(indiceColunaNumero));
                cartao.setCvv(cursor.getString(indiceColunaCvv));
                cartao.setVencimento(cursor.getString(indiceColunaValidade));

                cartaoList.add(cartao);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        db.close();

        return cartaoList;
    }

    public Usuario consultarUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projecao = {TABLE_USUARIO_COLUNA1, TABLE_USUARIO_COLUNA2, TABLE_USUARIO_COLUNA3, TABLE_USUARIO_COLUNA4, TABLE_USUARIO_COLUNA5};
        String selection = TABLE_USUARIO_COLUNA4 + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME_USUARIOS,
                projecao,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        return montaUsuario(cursor);

    }

    public Usuario montaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getString(cursor.getColumnIndex(TABLE_USUARIO_COLUNA1)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(TABLE_USUARIO_COLUNA5)));
        usuario.setUsername(cursor.getString(cursor.getColumnIndex(TABLE_USUARIO_COLUNA4)));
        return usuario;
    }

}
