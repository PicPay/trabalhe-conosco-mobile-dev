package com.example.vitor.picpaytest.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import com.example.vitor.picpaytest.dominio.Cartao;
import com.example.vitor.picpaytest.dominio.Historico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitor on 21/08/2017.
 */

public class PicPayDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";

    public static final String BANCO = "picpayteste.sqlite";
    private static final int VERSAO = 1;

    public PicPayDB(Context context){
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists cartao (_id integer primary key autoincrement not null, " +
                "numero char(16), cvv integer, vencimento char(5));");
        sqLiteDatabase.execSQL("create table if not exists historico(_id integer primary key autoincrement not null, " +
                "valor double, username text, cartao text, foto blob);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long salvaHistorico(double valor, String username, String cartao, byte[] foto){
        long id;
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("valor", valor);
            values.put("username", username);
            values.put("cartao", cartao);
            values.put("foto", foto);
            id = db.insert("historico", "", values);
            return id;
        } finally {
            db.close();
        }
    }

    public long salvaCartao(Cartao cartao){
        long id = cartao.getId();
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("numero", cartao.getNumero());
            values.put("cvv", cartao.getCvv());
            values.put("vencimento", cartao.getVencimento());
            if(id!=0){
                String identidade = String.valueOf(id);
                String[] argumento = new String[]{identidade};
                int count = db.update("cartao", values, "_id=?", argumento);
                return count;
            }else{
                id = db.insert("cartao", "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    public int deletarCartao(Cartao cartao){
        SQLiteDatabase db = getWritableDatabase();
        try{
            String[] argumento = new String[]{String.valueOf(cartao.getId())};
            int count = db.delete("cartao", "_id=?", argumento);
            return count;
        }finally {
            db.close();
        }
    }

    public List<Cartao> lista(){
        List<Cartao> cartoes = new ArrayList<Cartao>();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.query("cartao", null, null, null, null, null, null);

            if(cursor.moveToFirst()){
                do{
                    Cartao cartao = new Cartao();
                    cartao.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    cartao.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                    cartao.setCvv(cursor.getInt(cursor.getColumnIndex("cvv")));
                    cartao.setVencimento(cursor.getString(cursor.getColumnIndex("vencimento")));
                    cartoes.add(cartao);
                }while(cursor.moveToNext());
            }

        }finally {
            db.close();
        }

        return cartoes;
    }

    public List<Historico> listaHistorico(){
        List<Historico> hist = new ArrayList<Historico>();
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor cursor = db.query("historico", null, null, null, null, null, null);

            if(cursor.moveToFirst()){
                do{
                    Historico h = new Historico();
                    h.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    h.setValue(cursor.getDouble(cursor.getColumnIndex("valor")));
                    h.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    h.setCartao(cursor.getString(cursor.getColumnIndex("cartao")));
                    h.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                    hist.add(h);
                }while(cursor.moveToNext());
            }

        }finally {
            db.close();
        }

        return hist;
    }
}
