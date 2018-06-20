package br.com.gsas.app.picpay.Connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.gsas.app.picpay.DAO.CartaoDAO;
import br.com.gsas.app.picpay.Database.DatabaseManager;
import br.com.gsas.app.picpay.Domain.Cartao;

public class ServiceCartao {

    private CartaoDAO db;
    private Context context;


    public ServiceCartao(Context context){

        this.context = context;

        db = DatabaseManager.getInstance(context).getCartaoDAO();

    }


    @SuppressLint("StaticFieldLeak")
    public void trocarAtivoBD(final MyCallback<Cartao> callback, final Cartao cartao){

        new AsyncTask<Void, Void, List<Cartao>>(){

            @Override
            protected List<Cartao> doInBackground(Void... voids) {

                Log.d("Cartao", "Trocar Ativo no BD");

                Cartao antigo = db.findByAtivo();

                antigo.setAtivo(0);
                cartao.setAtivo(1);

                db.update(antigo);
                db.update(cartao);

                return db.getAll();
            }

            @Override
            protected void onPostExecute(List<Cartao> cartaos) {

                if(cartaos == null){
                    callback.failure("Erro ao Excluir");
                }
                else{

                    if(cartaos.size() == 0){
                        callback.empty();
                    }
                    else{
                        callback.sucess(cartaos);
                    }
                }

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAllBD(final MyCallback<Cartao> callback){

        new AsyncTask<Void, Void, List<Cartao>>(){

            @Override
            protected List<Cartao> doInBackground(Void... voids) {

                Log.d("Cartao", "getAllBD no BD");

                return db.getAll();
            }

            @Override
            protected void onPostExecute(List<Cartao> cartaos) {

                if(cartaos == null){

                    callback.failure("Retorno null");
                }
                else{

                    if(cartaos.size() == 0){
                        callback.empty();
                    }
                    else {
                        callback.sucess(cartaos);
                    }
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAtivoBD(final OnCallback<Cartao> callback){

        new AsyncTask<Void, Void, Cartao>(){

            @Override
            protected Cartao doInBackground(Void... voids) {

                Log.d("Cartao", "getAtivoBD no BD");

                return db.findByAtivo();

            }

            @Override
            protected void onPostExecute(Cartao cartao) {

                if(cartao != null){
                    callback.sucess(cartao);
                }
                else {
                    callback.failure("Erro pegar Cartao Ativo");
                }

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteBD(final MyCallback<Cartao> callback, final Cartao cartao){

        new AsyncTask<Void, Void, List<Cartao>>(){

            @Override
            protected List<Cartao> doInBackground(Void... voids) {

                Log.d("Cartao", "Excluindo no BD");

                db.delete(cartao);

                if(cartao.getAtivo() == 1){

                    List<Cartao> lista = db.getAll();

                    if(lista != null && lista.size() != 0){

                        Cartao atualiza = lista.get(0);
                        atualiza.setAtivo(1);
                        db.update(atualiza);
                    }
                }

                return db.getAll();
            }

            @Override
            protected void onPostExecute(List<Cartao> cartaos) {

                if(cartaos == null){

                    callback.failure("Retorno null");
                }
                else{

                    if(cartaos.size() == 0){
                        callback.empty();
                    }
                    else {
                        callback.sucess(cartaos);
                    }
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertBD(final OnCallback<Cartao> callback, final Cartao cartao){

        new AsyncTask<Void, Void, Cartao>(){

            @Override
            protected Cartao doInBackground(Void... voids) {

                Log.d("Cartao", "Salvando no BD");


                Cartao ativo = db.findByAtivo();

                if(ativo != null){

                    ativo.setAtivo(0);
                    db.update(ativo);
                }

                Cartao atualiza = db.findById(cartao.getId());

                if( atualiza == null){

                    db.insert(cartao);
                }
                else{
                    db.update(cartao);
                }
                return cartao;
            }

            @Override
            protected void onPostExecute(Cartao cartao) {

                callback.sucess(cartao);
            }
        }.execute();
    }
}
