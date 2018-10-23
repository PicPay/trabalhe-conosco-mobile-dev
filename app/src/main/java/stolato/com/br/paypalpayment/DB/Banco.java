package stolato.com.br.paypalpayment.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import stolato.com.br.paypalpayment.Model.Card;

public class Banco {
    private String sql,retorno;
    private SQLiteDatabase banco;
    private Cursor cursor;

    public void startBanco(Context ctx) {
        try {
            banco = ctx.openOrCreateDatabase("apppicpay",Context.MODE_PRIVATE,null);
            banco.execSQL("CREATE TABLE IF NOT EXISTS cards(id INTEGER PRIMARY KEY AUTOINCREMENT,nome VACHAR,number VACHAR,expiry VACHAR)");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int Indice(String indice){
        int ind = 0;
        try {
            ind = cursor.getColumnIndex(indice);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ind;
    }

    public void Insert(String nome,String number,String expiry){
        try {
           banco.execSQL("INSERT INTO cards (nome,number,expiry) VALUES ('"+nome+"','"+number+"','"+expiry+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Update(int id, String name,String number,String expiry){
        try {
            banco.execSQL("UPDATE cards SET nome = '"+name+"',number = '"+number+"', expiry = '"+expiry+"' WHERE id ="+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Cursor consulta(String sql){
        try{
            cursor = banco.rawQuery(sql,null);
            cursor.moveToFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return cursor;
    }


    public void Delete(int id){
        try {
            banco.execSQL("DELETE FROM cards WHERE id = "+id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Cursor getCards(){
        cursor = banco.rawQuery("SELECT * FROM cards",null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getCard(int id){
        cursor = banco.rawQuery("SELECT * FROM cards WHERE id = "+id,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Boolean checkCards(){
        boolean t = false;
        cursor = banco.rawQuery("SELECT * FROM cards",null);
        if(cursor.getCount() > 0){
            t = true;
        }
        return t;
    }


    public void close(){
        banco.close();
    }
}
