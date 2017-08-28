package com.tmontovaneli.picpaychallenge.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.tmontovaneli.picpaychallenge.model.Cartao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tmontovaneli on 23/08/17.
 */

public class CartaoDAO extends ChallengeDAO {


    public CartaoDAO(Context context) {
        super(context);
    }

    public void insere(Cartao cartao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nr_cartao", cartao.getNumero());
        dados.put("nr_cvv", cartao.getCvv());
        dados.put("dt_expiracao", cartao.getExpiracao().getTime());

        db.insert("Cartoes", null, dados);

        db.close();
    }


    public List<Cartao> buscaCartoes() {
        String sql = "SELECT * FROM Cartoes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Cartao> result = populaCartoes(c);
        c.close();

        return result;
    }

    @NonNull
    private List<Cartao> populaCartoes(Cursor c) {
        List<Cartao> cartoes = new ArrayList<Cartao>();
        while (c.moveToNext()) {
            Cartao cartao = new Cartao();
            cartao.setNumero(c.getString(c.getColumnIndex("nr_cartao")));
            cartao.setCvv(c.getString(c.getColumnIndex("nr_cvv")));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(c.getLong(c.getColumnIndex("dt_expiracao")));

            cartao.setExpiracao(calendar.getTime());

            cartoes.add(cartao);
        }
        return cartoes;
    }
}
