package com.picpay.picpayproject.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.picpay.picpayproject.Preferences;
import com.picpay.picpayproject.R;
import com.picpay.picpayproject.database.DatabaseHelper;
import com.picpay.picpayproject.model.Cartao;
import com.picpay.picpayproject.model.Transferencia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.picpay.picpayproject.Adapter.AMIGO_FOTO;
import static com.picpay.picpayproject.Adapter.AMIGO_ID;
import static com.picpay.picpayproject.Adapter.AMIGO_NOME;
import static com.picpay.picpayproject.Adapter.AMIGO_USERNAME;

public class SendMoneyActivity extends AppCompatActivity {

    private TextView id;
    private TextView nome;
    private TextView username;
    private ImageView foto;
    private Spinner cartoes;
    private EditText valor;
    private Cartao cartaoselecionado;
    private Button enviar;
    private String amigoID;
    private String amigoNome;
    private String amigoFoto;
    private String amigoUsername;
    private String usuarioID;
    private ArrayList<Cartao> listaCartao = new ArrayList<>();
    private ArrayList<String> listaNumCartao = new ArrayList<>();

    private void associacomponentes(){
        id = (TextView) findViewById(R.id.tv_sendmoney_id);
        nome = (TextView) findViewById(R.id.tv_sendmoney_nome);
        username = (TextView) findViewById(R.id.tv_sendmoney_username);
        foto = (ImageView) findViewById(R.id.iv_sendmoney_foto);
        cartoes = (Spinner) findViewById(R.id.spinner_selectcard);
        valor = (EditText) findViewById(R.id.et_sendmoney_valor);
        enviar = (Button) findViewById(R.id.button_enviar);

        amigoID = getIntent().getStringExtra(AMIGO_ID);
        amigoNome = getIntent().getStringExtra(AMIGO_NOME);
        amigoFoto = getIntent().getStringExtra(AMIGO_FOTO);
        amigoUsername = getIntent().getStringExtra(AMIGO_USERNAME);

        id.setText(amigoID);
        nome.setText(amigoNome);
        username.setText(amigoUsername);
        username.setTypeface(null, Typeface.BOLD);
        Glide.with(this).load(amigoFoto).into(foto);

        Preferences p = new Preferences(this);
        usuarioID = p.recuperaid();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        associacomponentes();
        DatabaseHelper db = new DatabaseHelper(this);
        listaCartao = db.consultarCartao(usuarioID);

        for (int i =0; i < listaCartao.size(); i++){
            Cartao cartao;
            cartao = listaCartao.get(i);

            listaNumCartao.add(cartao.getNumero());
        }

        ArrayAdapter<String> adapterCartao;
        adapterCartao = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listaNumCartao);
        adapterCartao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cartoes.setAdapter(adapterCartao);

        cartoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cartaoselecionado = listaCartao.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartaoselecionado.getNumero().equals("1111111111111111")) {
                    UploadJSON upload = new UploadJSON();
                    Transferencia transferencia = new Transferencia(cartaoselecionado.getNumero(),
                            cartaoselecionado.getCvv(),valor.getText().toString(),
                            cartaoselecionado.getVencimento(),amigoID);
                    upload.setTransferencia(transferencia);
                    upload.execute();
                }else {
                    AlertDialog dialog;
                    AlertDialog.Builder erro = new AlertDialog.Builder(SendMoneyActivity.this);
                    erro.setTitle("Erro:");
                    erro.setMessage("Cartão Recusado!");
                    erro.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(SendMoneyActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    });

                    dialog = erro.create();
                    dialog.show();
                }

            }
        });

    }

    class UploadJSON extends AsyncTask<String, Void, Transferencia> {
        private Transferencia transferencia;
        ProgressDialog dialog;

        public void setTransferencia(Transferencia transferencia) {
            this.transferencia = transferencia;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(SendMoneyActivity.this, "Aguarde","Executando Transferencia");
        }

        protected Transferencia doInBackground(String... params) {
            String urlString = "http://careers.picpay.com/tests/mobdev/transaction";
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("card_number", this.transferencia.getCardNumber());
                jsonObj.put("cvv", this.transferencia.getCvv());
                jsonObj.put("value", this.transferencia.getValue());
                jsonObj.put("expiry_date", this.transferencia.getExpiryDate());
                jsonObj.put("destination_user_id", this.transferencia.getDestinationUser());

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setUseCaches(false);
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.connect();
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(jsonObj.toString().getBytes("UTF-8"));
                    os.close();
                    this.transferencia.setConcluido(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this.transferencia;
        }

        protected void onPostExecute(Transferencia transferencia) {
            super.onPostExecute(transferencia);
            dialog.dismiss();
            if (this.transferencia.getConcluido()) {
                AlertDialog dialog;
                AlertDialog.Builder success = new AlertDialog.Builder(SendMoneyActivity.this);
                success.setTitle("Transferência:");
                success.setMessage("Transferência realizada com sucesso!");
                success.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(SendMoneyActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                });
                dialog = success.create();
                dialog.show();
            } else {
                AlertDialog dialog;
                AlertDialog.Builder error = new AlertDialog.Builder(SendMoneyActivity.this);
                error.setTitle("Erro:");
                error.setMessage("Problemas ao fazer a transferência. Tente novamente");
                dialog = error.create();
                dialog.show();
            }
        }
    }
}
