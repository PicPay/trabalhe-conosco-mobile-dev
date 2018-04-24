package com.example.filipe.enviadinheiro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.filipe.application.MyApplication;
import com.example.filipe.model.CartaoCredito;
import com.example.filipe.model.User;
import com.example.filipe.util.CircleTransform;
import com.example.filipe.util.MascaraMonetaria;
import com.example.filipe.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TransacaoActivity extends AppCompatActivity {

    static final int PICK_CARTAO_REQUEST = 1;

    private Toolbar mToolbar;

    private User mUser;
    private String mValor;
    private CartaoCredito mCartaoCredito;

    private TextView mTxt_name;
    private ImageView mImageView;
    private EditText mEdt_valor;
    private Button mBtn_pagar;
    private ProgressDialog progressDialog;

    private Realm mRealm;
    private RealmResults<CartaoCredito> mListaCartoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUser = getIntent().getParcelableExtra("usuario");

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        mListaCartoes = mRealm.where(CartaoCredito.class).findAll();

        if(mUser == null){
            finish();
        }

        mTxt_name = (TextView) findViewById(R.id.txt_name);
        mTxt_name.setText(mUser.getName());

        mImageView = (ImageView) findViewById(R.id.imv_img);
        Glide.with(this).load(mUser.getImg())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);


        mEdt_valor = (EditText) findViewById(R.id.edt_valor);
        mEdt_valor.setSelection(mEdt_valor.getText().length());
        mEdt_valor.addTextChangedListener(new MascaraMonetaria(mEdt_valor));

        mBtn_pagar = (Button) findViewById(R.id.btn_pagar);
        mBtn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagar();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void pagar(){
        if(!validar()){
            return;
        }

        mBtn_pagar.setEnabled(false);

        progressDialog = new ProgressDialog(TransacaoActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Estamos realizando a transação...");
        progressDialog.show();

        String valor_string = mEdt_valor.getText().toString();
        String valor_final = valor_string.substring(2);
        mValor = valor_final.replace(",", ".");

        //TODO VERFICAR SE JÁ TEM UM CARTÃO DE CREDITO CADASTRADO

        if(mListaCartoes.size() > 0) {

            transacao();
        }else{
            progressDialog.dismiss();
            Intent intent = new Intent(this, SemCartaoActivity.class);
            intent.putExtra("usuario", mUser);
            startActivityForResult(intent, PICK_CARTAO_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CARTAO_REQUEST){
            if(resultCode == RESULT_OK){
                mBtn_pagar.setEnabled(true);
                mListaCartoes = mRealm.where(CartaoCredito.class).findAll();
            }
        }
    }

    private void transacao(){

        mCartaoCredito = mListaCartoes.first();

        transacaoOnline();

    }

    private boolean validar(){
        boolean valido = true;

        String valor = mEdt_valor.getText().toString();

        if(valor.isEmpty() || valor.equals("R$0,00")) {
            mEdt_valor.setError("um valor é necessario");
            valido = false;
        }else {
            mEdt_valor.setError(null);
        }

        return valido;
    }

    private void transacaoOnline(){

        if(Util.verificaConexaoInternet(this)){

            String tag = "json_array_request";
            String url = " http://careers.picpay.com/tests/mobdev/transaction";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("card_number", mCartaoCredito.getCard_number());
                jsonBody.put("cvv", mCartaoCredito.getCvv());
                jsonBody.put("expiry_date", mCartaoCredito.getExpiry_date());
                jsonBody.put("value", mValor);
                jsonBody.put("destination_user_id", mUser.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject jsonTransaction = response.getJSONObject("transaction");
                                boolean success = jsonTransaction.getBoolean("success");

                                if(success){

                                    Intent returnItent = TransacaoActivity.this.getIntent();
                                    returnItent.putExtra("", "");
                                    setResult(RESULT_OK, returnItent);
                                    finish();

                                }else{

                                    Realm.init(TransacaoActivity.this);
                                    Realm realm = Realm.getDefaultInstance();

                                    realm.beginTransaction();

                                    RealmResults<CartaoCredito> listaCartoes = realm.where(CartaoCredito.class).findAll();

                                    listaCartoes.deleteAllFromRealm();

                                    realm.commitTransaction();

                                    Snackbar.make(TransacaoActivity.this.findViewById(R.id.coordinator),
                                            "Falha ao realizar a transação",
                                            Snackbar.LENGTH_LONG)
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mBtn_pagar.setEnabled(true);
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("LOG", "naõ foi");
                        }
                    }
            );

            MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, tag);

        }else{
            Snackbar.make(TransacaoActivity.this.findViewById(R.id.coordinator),
                    "É necessario conexão com a internet",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
