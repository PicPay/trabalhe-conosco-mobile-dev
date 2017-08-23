package com.example.vitor.picpaytest.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Cartao;
import com.example.vitor.picpaytest.dominio.Pagamento;
import com.example.vitor.picpaytest.dominio.Usuario;
import com.example.vitor.picpaytest.fragment.ListFragment;
import com.example.vitor.picpaytest.helper.FormataValor;
import com.example.vitor.picpaytest.persistencia.PicPayDB;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransferenciaActivity extends AppCompatActivity {

    TextView username, name;
    private EditText valor;
    private RadioButton radioButton;
    private Cartao cartao;
    private List<Cartao> cartoes;
    private int indice;
    private List<String> items;
    private boolean selecionado, sucesso;
    private Usuario usuario;
    private Pagamento pagamento;
    private ImageView img;
    private Button botao;
    private ArrayAdapter<String> adaptador;
    private boolean conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);
        setupToolbar();

        cartao = new Cartao();

        usuario = new Usuario();

        Intent intent = getIntent();

        Bundle parametros = intent.getExtras();

        usuario.setId(parametros.getInt("id"));
        usuario.setUsername(parametros.getString("username"));
        usuario.setName(parametros.getString("name"));
        usuario.setImg(parametros.getString("img"));
        usuario.setImagem((Bitmap) intent.getParcelableExtra("imagem"));

        setupComponents();

        Locale mLocale = new Locale("pt", "BR");

        valor.addTextChangedListener(new FormataValor(valor, mLocale));

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                conexao = testaConexao(TransferenciaActivity.this);

                if(conexao){
                    if(!radioButton.isChecked()){
                        Toast.makeText(TransferenciaActivity.this, "Cartão Não Selecionado", Toast.LENGTH_SHORT).show();
                    }else if(valor.getText().toString().equals("R$0,00")){
                        Toast.makeText(TransferenciaActivity.this, "Insira o Valor Desejado", Toast.LENGTH_SHORT).show();
                    }else{
                        String auxiliar = valor.getText().toString().substring(2);
                        auxiliar = auxiliar.replace(',', '.');
                        double valorConvertido = Double.parseDouble(auxiliar);
                        pagamento = new Pagamento();
                        pagamento.setValue(valorConvertido);
                        pagamento.setCard_number(cartoes.get(indice).getNumero());
                        pagamento.setCvv(cartoes.get(indice).getCvv());
                        pagamento.setExpiry_date(cartoes.get(indice).getVencimento());
                        pagamento.setDestination_user_id(usuario.getId());

                        Gson gson = new Gson();
                        String json;
                        json = gson.toJson(pagamento);

                        EnviaPagamento pagamento = new EnviaPagamento(TransferenciaActivity.this);
                        pagamento.execute();

                    }
                }else{
                    Toast.makeText(TransferenciaActivity.this, "Erro de conexão - Verifique sua Conexão ou Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    finish();
                }


            }
        });

        items = new ArrayList<String>();

        PicPayDB db =  new PicPayDB(this);
        try{
            cartoes = db.lista();
            for(int i = 0; i < cartoes.size(); i++){
                items.add(cartoes.get(i).getNumero());
            }
        }finally {
            db.close();
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                selecionado = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Selecione o Cartão");
                builder.setSingleChoiceItems(adaptador, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        radioButton.setText(adaptador.getItem(i));
                        indice = i;
                        selecionado = true;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selecionado){
                            radioButton.setChecked(true);
                            Toast.makeText(view.getContext(), "Cartão Selecionado", Toast.LENGTH_SHORT).show();
                        }else{
                            radioButton.setChecked(false);
                            Toast.makeText(view.getContext(), "Seleção Inválida - Escolha um cartão", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        radioButton.setChecked(false);
                        radioButton.setText("Selecione o Cartão");
                        Toast.makeText(view.getContext(), "Seleção Cancelada pelo Usuário", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setupComponents(){
        username = (TextView) findViewById(R.id.username_transferencia);
        name = (TextView) findViewById(R.id.name_transferencia);
        img = (ImageView) findViewById(R.id.img_transferencia);
        radioButton = (RadioButton) findViewById(R.id.cartao_transferencia);
        username.setText(usuario.getUsername());
        name.setText(usuario.getName());
        img.setImageBitmap(usuario.getImagem());
        valor = (EditText) findViewById(R.id.valor);
        botao = (Button) findViewById(R.id.pagar_transferencia);
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Transferência");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private class EnviaPagamento extends AsyncTask<Void, Void, Void>{
        private Context context;
        private static final String ENDPOINT = "http://careers.picpay.com/tests/mobdev/transaction";

        public EnviaPagamento(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL(ENDPOINT);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestMethod("POST");

                JSONObject json = new JSONObject();
                json.put("card_number", pagamento.getCard_number());
                json.put("cvv", pagamento.getCvv());
                json.put("value", pagamento.getValue());
                json.put("expiry_date", pagamento.getExpiry_date());
                json.put("destination_user_id", pagamento.getDestination_user_id());

                DataOutputStream localDataOutputStream = new DataOutputStream(con.getOutputStream());
                localDataOutputStream.writeBytes(json.toString());
                localDataOutputStream.flush();
                localDataOutputStream.close();

                StringBuilder sb = new StringBuilder();
                int HttpResult = con.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"));
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    String str = sb.toString();

                    JSONObject root = new JSONObject(str);
                    JSONObject obj = root.getJSONObject("transaction");

                    String status = obj.optString("status");
                    sucesso = obj.optBoolean("success");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(sucesso){
                                PicPayDB db = new PicPayDB(context);
                                try{
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    Bitmap bitmap = usuario.getImagem();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byte[] byteArray;
                                    byteArray = stream.toByteArray();
                                    db.salvaHistorico(pagamento.getValue(), usuario.getUsername(), pagamento.getCard_number(), byteArray);
                                    Log.e("TESTE", "SALVOU");
                                }finally {
                                    db.close();
                                }

                                Toast.makeText(context, "Pagamento Aprovado - SUCESSO!", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(context, "Ops! Ocorreu algum erro na transação, verifique seu cartão e/ou valor informado!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    System.out.println(con.getResponseMessage());
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private boolean testaConexao(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}