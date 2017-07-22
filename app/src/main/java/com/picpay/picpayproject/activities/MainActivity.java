package com.picpay.picpayproject.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.picpay.picpayproject.Adapter;
import com.picpay.picpayproject.Preferences;
import com.picpay.picpayproject.R;
import com.picpay.picpayproject.database.DatabaseHelper;
import com.picpay.picpayproject.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText numero;
    private EditText validade;
    private EditText cvv;
    private Button cadCartao;
    private View addCard;

    private void associaComponentes() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        addCard = inflater.inflate(R.layout.dialog_addcard, null);

        numero = (EditText) addCard.findViewById(R.id.et_addcard_numcard);
        validade = (EditText) addCard.findViewById(R.id.et_addcard_validade);
        cvv = (EditText) addCard.findViewById(R.id.et_addcard_cvv);
        cadCartao = (Button) addCard.findViewById(R.id.button_addcard);

        SimpleMaskFormatter simpleMaskNumero = new SimpleMaskFormatter("NNNN NNNN NNNN NNNN");
        MaskTextWatcher maskNumero = new MaskTextWatcher(numero, simpleMaskNumero);
        numero.addTextChangedListener(maskNumero);

        SimpleMaskFormatter simpleMaskCvv = new SimpleMaskFormatter("NNN");
        MaskTextWatcher maskCvv = new MaskTextWatcher(cvv, simpleMaskCvv);
        cvv.addTextChangedListener(maskCvv);

        SimpleMaskFormatter simpleMaskValidade = new SimpleMaskFormatter("NN/NN");
        MaskTextWatcher maskValidade = new MaskTextWatcher(validade, simpleMaskValidade);
        validade.addTextChangedListener(maskValidade);
    }

    private void salvaCartao() {
        Preferences p = new Preferences(this);
        String idUser = p.recuperaid();
        String num = numero.getText().toString();
        num.replaceAll(" ", "");

        DatabaseHelper db = new DatabaseHelper(this);
        db.inserirCartao(idUser, num, cvv.getText().toString(), validade.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        associaComponentes();

        new ConsomeJson().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_addcard:
                cadastrarCartao();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarUsuario() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);

    }

    public void cadastrarCartao() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(addCard);
        builder.setTitle("Novo Cartão");
        final AlertDialog dialog = builder.create();

        cadCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(numero.getText().toString().equals("")) && !(cvv.getText().toString().equals("")) &&
                        !(validade.getText().toString().equals("")) && numero.getText().toString().length() > 15 &&
                        cvv.getText().toString().length() == 3 && validade.getText().toString().length() > 3) {
                    salvaCartao();
                    dialog.dismiss();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Erro")
                            .setMessage("Não foi possivel adicionar o cartão")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        dialog.show();
    }

    private void criaRv(final List<Usuario> usuarios) {
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.rv_lista_usuarios);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Adapter adapter = new Adapter(MainActivity.this, usuarios);
        recyclerView.setAdapter(adapter);
        recyclerView.setClickable(true);
    }

    class ConsomeJson extends AsyncTask<String, Void, List<Usuario>> {

        ProgressDialog dialog;
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Carregando...");
        }

        protected List<Usuario> doInBackground(String... params) {
            String urlString = "http://careers.picpay.com/tests/mobdev/users";
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            List<Usuario> usuarios = null;
            try {
                usuarios = getUsuarios(result.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return usuarios;
        }

        private List<Usuario> getUsuarios(String jsonString) throws JSONException {
            List<Usuario> usuarios = new ArrayList<>();
            JSONArray usuarioList = new JSONArray(jsonString);
            JSONObject usuario;

            for (int i = 0; i < usuarioList.length(); i++) {
                usuario = usuarioList.getJSONObject(i);

                Usuario objetoUsuario = new Usuario();
                objetoUsuario.setId(usuario.getString("id"));
                objetoUsuario.setNome(usuario.getString("name"));
                objetoUsuario.setImageLink(usuario.getString("img"));
                objetoUsuario.setUsername(usuario.getString("username"));

                usuarios.add(objetoUsuario);
            }
            return usuarios;
        }

        @Override
        protected void onPostExecute(List<Usuario> result) {
            super.onPostExecute(result);
            final List<Usuario> usuarios = result;
            dialog.dismiss();

            if (result.size() > 0) {
                criaRv(usuarios);

            } else {
                AlertDialog alerta;
                AlertDialog.Builder error = new AlertDialog.Builder(MainActivity.this);
                error.setTitle("Erro:");
                error.setMessage("Json Não foi carregado!");
                alerta = error.create();
                alerta.show();
            }
        }
    }


}

