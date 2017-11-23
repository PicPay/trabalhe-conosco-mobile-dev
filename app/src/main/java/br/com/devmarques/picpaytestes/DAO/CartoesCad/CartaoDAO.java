package br.com.devmarques.picpaytestes.DAO.CartoesCad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.Dados.Cartao;

/**
 * Created by igornobre on 18/11/16.
 */

public class CartaoDAO {

    Context context;
    BancoCardDAO dao;
    private static final String BANCONOME = "Cartoes";

    public CartaoDAO(Context context) {
        this.context = context;
    }

    public long insere(Cartao cartao){
        dao = new BancoCardDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosDoContato(cartao);
        long inserir = db.insert(BANCONOME, null, dados);
        db.close();
        Log.i(BANCONOME, inserir + "");
        return inserir;
    }

    public ArrayList<Cartao> buscaContatos(){
        String sql = "SELECT * FROM " + BANCONOME;
        dao = new BancoCardDAO(context);
        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        ArrayList<Cartao> Cartao = new ArrayList<Cartao>();
        while (c.moveToNext()){
            Cartao card = new Cartao();
            card.setId(c.getInt(c.getColumnIndex("id")));
            card.setNomeCard(c.getString(c.getColumnIndex("nomeCard")));
            card.setCardNumber(c.getString(c.getColumnIndex("cardNumber")));
            card.setCvv(c.getString(c.getColumnIndex("cvv")));
            card.setExpirationDate(c.getString(c.getColumnIndex("expiryDate")));
            Cartao.add(card);
        }
        c.close();
        return Cartao;

    }

    public void alterar(Cartao cartao){
        dao = new BancoCardDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosDoContato(cartao);
        String[] params = {String.valueOf(cartao.getId())};
        db.update(BANCONOME, dados, "id=?", params);
    }

    public void Deletar(Cartao cartao){
        dao = new BancoCardDAO(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = new String[]{String.valueOf(cartao.getId())};
        db.delete(BANCONOME, whereClause, whereArgs);
        db.close();
    }

    private ContentValues pegaDadosDoContato(Cartao cartao) {
        ContentValues dados = new ContentValues();
        dados.put("nomeCard", cartao.getNomeCard());
        dados.put("cardNumber", cartao.getCardNumber());
        dados.put("cvv", cartao.getCvv());
        dados.put("expiryDate", cartao.getExpirationDate());
        return dados;
    }

}








