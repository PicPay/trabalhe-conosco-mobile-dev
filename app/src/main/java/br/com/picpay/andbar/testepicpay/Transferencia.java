package br.com.picpay.andbar.testepicpay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.picpay.andbar.testepicpay.base.DatabaseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by andba on 21/04/2018.
 */

public class Transferencia extends AppCompatActivity
{
    private TextView txvNomePessoa;
    private ImageView imgFotoPessoa;
    private EditText txvValor;
    private Button btnOK;
    private Button btnCancelar;
    private Spinner spnCartoes;
    private Button btnCadastrar;

    DatabaseHandler db;

    private static String urlPost = "http://careers.picpay.com/tests/mobdev/transaction";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferencia);

        db = new DatabaseHandler(this);

        //region Mapeamento de itens na tela
        txvNomePessoa = (TextView)findViewById(R.id.txv_nome);
        imgFotoPessoa = (ImageView)findViewById(R.id.img_foto);
        txvValor = (EditText)findViewById(R.id.txt_valor);

        txvNomePessoa.setText(Pessoa.PessoaSingleton.getName());
        imgFotoPessoa.setImageBitmap(Pessoa.PessoaSingleton.getImagem());

        btnOK = (Button)findViewById(R.id.btn_ok);
        btnCancelar = (Button)findViewById(R.id.btn_cancelar);
        btnCadastrar = (Button)findViewById(R.id.btn_cadastra_cartao);

        //endregion

        //region Alimenta Dropdown Cartões
        List<String> listaCartoes = new ArrayList<>();


        for(CartaoUsuario item: db.getAllCards(DatabaseHandler.TABLE_CARDS))
        {
            listaCartoes.add(((CartaoUsuario)item).NumeroCartao);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listaCartoes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCartoes = (Spinner)findViewById(R.id.spn_cartoes);
        spnCartoes.setAdapter(adapter);

        //endregion

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transferir(view);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastrarCartao(view);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cancelar(view);
        }
    });

    }



    private void Transferir(View view)
    {
        CartaoUsuario cartao = new CartaoUsuario();

        List<CartaoUsuario> cartoes = db.getAllCards(DatabaseHandler.TABLE_CARDS);

        if(cartoes.isEmpty())
        {
            Toast.makeText(this, "Não existem cartões cadastrados. Cadastre um cartão.", Toast.LENGTH_LONG).show();
        }
        else
        {
            CartaoUsuario.CartaoSingleton = new CartaoUsuario();
            CartaoUsuario.CartaoSingleton = db.getCard(DatabaseHandler.TABLE_CARDS, spnCartoes.getSelectedItem().toString());

            Transacao.TransacaoSingleton = new Transacao();
            Transacao.TransacaoSingleton.Cartao = CartaoUsuario.CartaoSingleton;
            Transacao.TransacaoSingleton.Destinatario = Pessoa.PessoaSingleton.getId();
            Transacao.TransacaoSingleton.Valor = Double.parseDouble(txvValor.getText().toString());

            new HttpAsyncTask().execute(urlPost);
        }

    }

    private void CadastrarCartao(View view)
    {
        Intent detalhe = new Intent(Transferencia.this, CadastraCartao.class);
        startActivityForResult(detalhe,0);
    }

    private void Cancelar(View view)
    {
        finish();
    }

    public static String POST(String pUrl)
    {
        InputStream inputStream = null;
        String result = "";

        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(pUrl);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("card_number",Transacao.TransacaoSingleton.Cartao.NumeroCartao);
            jsonObject.put("cvv", Transacao.TransacaoSingleton.Cartao.CodigoValidacao);
            jsonObject.put("value", String.valueOf(Transacao.TransacaoSingleton.Valor));
            jsonObject.put("expiry_date",Transacao.TransacaoSingleton.Cartao.ValidadeCartao);
            jsonObject.put("destination_user_id", String.valueOf(Transacao.TransacaoSingleton.Destinatario));

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";



        JSONObject resposta = new JSONObject(result);
        resposta = resposta.getJSONObject("transaction");

        Transacao.TransacaoSingleton.RespostaProcessamento = resposta.getString("status");

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            Toast.makeText(getBaseContext(),"Resposta: " + Transacao.TransacaoSingleton.RespostaProcessamento,
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
