package br.com.devmarques.picpaytestes.DAO.HistoryTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.devmarques.picpaytestes.DAO.CartoesCad.BancoCardDAO;
import br.com.devmarques.picpaytestes.Dados.Transacoes;

/**
 * Created by Roger on 10/11/2017.
 */

public class TransacoesDAO {
    Context context;

    BancoTransacoes dao;
    private static final String BANCONOME = "Transacoes";

    public TransacoesDAO(Context context) {
        this.context = context;
    }

    public long insere(Transacoes transacoes) {
        dao = new BancoTransacoes(context);
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues dados = pegaDadosDoContato(transacoes);
        long inserir = db.insert(BANCONOME, null, dados);
        db.close();
        Log.i(BANCONOME, inserir + "");
        return inserir;
    }

    public ArrayList<Transacoes> buscaContatos() {
        String sql = "SELECT * FROM " + BANCONOME;
        dao = new BancoTransacoes(context);
        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        ArrayList<Transacoes> transacoess = new ArrayList<Transacoes>();
        while (c.moveToNext()) {
            Transacoes transacoes = new Transacoes();

            transacoes.setId(c.getInt(c.getColumnIndex("id")));
            transacoes.setTransacaoNum(c.getString(c.getColumnIndex("transacaoNum")));
            transacoes.setValor(c.getString(c.getColumnIndex("valor")));
            transacoes.setStatus(c.getString(c.getColumnIndex("status")));
            transacoes.setFotoperfil(c.getString(c.getColumnIndex("foto")));
            transacoes.setUser(c.getString(c.getColumnIndex("user")));
            transacoes.setCardNameandNumber(c.getString(c.getColumnIndex("cardNumberandName")));
            transacoes.setNome(c.getString(c.getColumnIndex("nome")));
            transacoes.setAprovada(c.getString(c.getColumnIndex("aprovada")));

            transacoess.add(transacoes);
        }
        c.close();
        return transacoess;

    }

    private ContentValues pegaDadosDoContato(Transacoes transacoes) {
        ContentValues dados = new ContentValues();
        dados.put("transacaoNum", transacoes.getTransacaoNum());
        dados.put("valor", transacoes.getValor());
        dados.put("status", transacoes.getStatus());
        dados.put("foto", transacoes.getFotoperfil());
        dados.put("user", transacoes.getUser());
        dados.put("cardNumberandName", transacoes.getCardNameandNumber());
        dados.put("nome",transacoes.getNome());
        dados.put("aprovada",transacoes.getAprovada());

        return dados;
    }
}