package picpayteste.devmarques.com.picpay_teste.dao.cartao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import picpayteste.devmarques.com.picpay_teste.dados.lista.cartao.Cartao;

public class CartaoDaoDB {

    Context context;
    BancoCardDao dao;
    private static final String BANCONOME = "Cartoes";

    public CartaoDaoDB(Context context) {
        this.context = context;
    }

    public long inserir_cartao(Cartao cartao){
        dao = new BancoCardDao(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = getItemCartao(cartao);
        long inserir = db.insert(BANCONOME, null, dados);
        db.close();
        Log.i(BANCONOME, inserir + "");
        return inserir;
    }

    public ArrayList<Cartao> listar_cartoes(){
        String sql = "SELECT * FROM " + BANCONOME;
        dao = new BancoCardDao(context);
        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        ArrayList<Cartao> Cartao = new ArrayList<Cartao>();
        while (c.moveToNext()){
            Cartao card = new Cartao();
            card.setId(c.getInt(c.getColumnIndex("id")));
            card.setNome_titular(c.getString(c.getColumnIndex("nomeCard")));
            card.setCard_number(c.getString(c.getColumnIndex("cardNumber")));
            card.setCvv(c.getString(c.getColumnIndex("cvv")));
            card.setExpiry_date(c.getString(c.getColumnIndex("expiryDate")));
            Cartao.add(card);
        }
        c.close();
        return Cartao;

    }

    public void alterar_cartao(Cartao cartao){
        dao = new BancoCardDao(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = getItemCartao(cartao);
        String[] params = {String.valueOf(cartao.getId())};
        db.update(BANCONOME, dados, "id=?", params);
    }

    public void Deletar_cartao(Cartao cartao){
        dao = new BancoCardDao(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = new String[]{String.valueOf(cartao.getId())};
        db.delete(BANCONOME, whereClause, whereArgs);
        db.close();
    }

    private ContentValues getItemCartao(Cartao cartao) {
        ContentValues dados = new ContentValues();
        dados.put("nomeCard", cartao.getNome_titular());
        dados.put("cardNumber", cartao.getCard_number());
        dados.put("cvv", cartao.getCvv());
        dados.put("expiryDate", cartao.getExpiry_date());
        return dados;
    }

}
