package com.oliveira.edney.picpay;

import android.os.AsyncTask;
import android.util.Log;
import com.oliveira.edney.picpay.Class.Transaction;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* Classe para realizar um POST da transação de pagamento */
public class Payment extends AsyncTask<String, Void, JSONObject> {

    private Callback callback;
    private Transaction transaction;

    public interface Callback{
        void onTransaction(JSONObject response);
    }

    public Payment(Transaction transaction, Callback callback){

        this.callback = callback;
        this.transaction = transaction;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {

        HttpURLConnection conexao = null;
        JSONObject resposta = new JSONObject();

        try {

            URL url = new URL(urls[0]);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            JSONObject jsonAtributos = new JSONObject();
            jsonAtributos.put("card_number", transaction.getNumeroCartao());
            jsonAtributos.put("cvv", transaction.getCvv());
            jsonAtributos.put("value", transaction.getValor());
            jsonAtributos.put("expiry_date", transaction.getDataExpiracao());
            jsonAtributos.put("destination_user_id", transaction.getIdDestino());

            OutputStream outputS = conexao.getOutputStream();
            outputS.write(jsonAtributos.toString().getBytes("UTF-8"));
            outputS.close();

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStreamReader inputSR = new InputStreamReader(conexao.getInputStream());
                BufferedReader reader = new BufferedReader(inputSR);
                String aux;

                aux = reader.readLine();
                JSONObject jsonResposta = new JSONObject(aux);
                JSONObject jsonAux = new JSONObject(jsonResposta.getString("transaction"));

                resposta.put("id", jsonAux.getString("id"));
                resposta.put("status", jsonAux.getString("status"));

                reader.close();
            }
        }
        catch (Exception e){
            Log.e("MeuErro", e.getMessage());
        }
        finally {

            if (conexao != null) {
                conexao.disconnect();
            }
        }

        return resposta;
    }

    @Override
    protected void onPostExecute(JSONObject resposta) {
        super.onPostExecute(resposta);

        callback.onTransaction(resposta);
    }
}
