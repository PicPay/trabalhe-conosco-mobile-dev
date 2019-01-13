package picpayteste.devmarques.com.picpay_teste.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import picpayteste.devmarques.com.picpay_teste.R;
import picpayteste.devmarques.com.picpay_teste.dados.lista.cartao.Cartao;
import picpayteste.devmarques.com.picpay_teste.dados.lista.transacao.Transacoes;
import picpayteste.devmarques.com.picpay_teste.dados.lista.transacao.Transaction;
import picpayteste.devmarques.com.picpay_teste.dados.lista.usuario.Usuario;
import picpayteste.devmarques.com.picpay_teste.utils.Online;
import picpayteste.devmarques.com.picpay_teste.utils.PayTextWatcher;

public class Pagamento extends AppCompatActivity {

    private Usuario usuarioSelecionado;
    private Cartao cartao;
    private TextView idUser;
    private TextView nomeCartao, editarCartao, rs_dinheiro;
    private EditText valor;
    private CircleImageView userImage;
    private Button pagar;
    private Online online;
    private ImageView voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        // inicializando ids
        inicializador_ids();

        online = new Online(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(Usuario.PARAM_USER_SELECTED) && extras.containsKey(Cartao.PARAM_CARD)) {
            usuarioSelecionado = (Usuario) extras.getSerializable(Usuario.PARAM_USER_SELECTED);
            cartao = (Cartao) extras.getSerializable(Cartao.PARAM_CARD);
        }

        final PayTextWatcher ptw = new PayTextWatcher(valor, "%,.2f");
        valor.addTextChangedListener(ptw);

        pagar.setBackground(ContextCompat.getDrawable(this, R.drawable.bt_disabled));
        pagar.setEnabled(false);

        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    rs_dinheiro.setTextColor(getColor(R.color.button_color));
                    valor.setTextColor(getColor(R.color.button_color));
                }
                if (!s.toString().equalsIgnoreCase("0,00")) {
                    pagar.setEnabled(true);
                    pagar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bt_cadastro));
                }else {
                    pagar.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Picasso.get()
                .load(usuarioSelecionado.getImg())
                .fit()
                .centerInside()
                .into(userImage);

        idUser.setText(usuarioSelecionado.getUsername());

        String iniciaisCartao = cartao.getCard_number().substring(0, 4);
        String dados = cartao.getNome_titular() + " " + iniciaisCartao + " . ";

        nomeCartao.setText(dados);

        editarCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CadastrandoCartao.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Cartao.PARAM_CARD_EDIT, cartao);
                i.putExtra(Usuario.PARAM_USER_SELECTED, usuarioSelecionado);
                startActivity(i);
            }
        });

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(valor.getText().toString().equalsIgnoreCase("0,00"))) {
                    if (online.haveNetworkConnection()) {
                        new SendPostRequest().execute();
                    }
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void inicializador_ids() {
        idUser = findViewById(R.id.user_id);
        userImage = findViewById(R.id.image_user);
        valor = findViewById(R.id.valor_pagamento);
        pagar = findViewById(R.id.finalizar_pagamento);
        nomeCartao = findViewById(R.id.nome_cartao);
        editarCartao = findViewById(R.id.editar_cartao);
        rs_dinheiro = findViewById(R.id.rs_dinheiro);
        voltar = findViewById(R.id.voltar);
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://careers.picpay.com/tests/mobdev/transaction"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("card_number", cartao.getCard_number());
                postDataParams.put("cvv", cartao.getCvv());
                postDataParams.put("value", valor.getText().toString());
                postDataParams.put("expiry_date", cartao.getExpiry_date());
                postDataParams.put("destination_user_id", usuarioSelecionado.getId());

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(postDataParams.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);

                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }


        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            Transaction transaction = gson.fromJson(result, Transaction.class);

            if (transaction.getTransaction().isSuccess()){
                Intent intent = new Intent(Pagamento.this, Recibo.class);
                intent.putExtra(Transaction.PARAM_TRANSACTION, transaction);
                intent.putExtra(Cartao.PARAM_CARD, cartao);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Seu pagamento foi recusado!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
