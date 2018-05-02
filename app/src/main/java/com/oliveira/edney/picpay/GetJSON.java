package com.oliveira.edney.picpay;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* Classe para captura de um array de JSON */
public class GetJSON extends AsyncTask<String, Void, JSONArray> {

    private Callback callback;

    public interface Callback{
        void onResponse(JSONArray jsonArray);
    }

    public GetJSON(Callback callback){

        this.callback = callback;
    }

    @Override
    protected JSONArray doInBackground(String... urls) {

        JSONArray jsonArray = null;
        URL url;
        HttpURLConnection conexao = null;

        try{

            url = new URL(urls[0]);
            conexao = (HttpURLConnection)url.openConnection();

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader inputSR = new InputStreamReader(conexao.getInputStream());
                BufferedReader reader = new BufferedReader(inputSR);

                String aux = reader.readLine();
                jsonArray = new JSONArray(aux);
            }
        }
        catch (Exception e){
            Log.e("MeuErro", e.getMessage());
        }
        finally {

            if(conexao != null){
                conexao.disconnect();
            }
        }

        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray resposta) {
        super.onPostExecute(resposta);

        callback.onResponse(resposta);
    }
}
