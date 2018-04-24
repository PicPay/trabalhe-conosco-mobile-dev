package com.example.filipe.enviadinheiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filipe.model.CartaoCredito;
import com.example.filipe.model.User;
import com.example.filipe.util.MascaraCartaoCredito;

import io.realm.Realm;

public class SemCartaoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mEdt_numero_cartao;
    private EditText mEdt_data_validade;
    private EditText mEdt_cvv;
    private Button mBtn_salvar;

    private User mUser;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_cartao);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUser = getIntent().getParcelableExtra("usuario");

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();


        mEdt_numero_cartao = (EditText) findViewById(R.id.edt_numero_cartao);
        mEdt_numero_cartao.addTextChangedListener(new MascaraCartaoCredito(mEdt_numero_cartao, MascaraCartaoCredito.FORMAT_NUMBER));

        mEdt_data_validade = (EditText) findViewById(R.id.edt_data_validade);
        mEdt_data_validade.addTextChangedListener(new MascaraCartaoCredito(mEdt_data_validade, MascaraCartaoCredito.FORMAT_DATE));
        mEdt_cvv = (EditText) findViewById(R.id.edt_cvv);

        mBtn_salvar = (Button) findViewById(R.id.btn_salvar);
        mBtn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
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

    private void salvar(){
        if(!validar()){
            return;
        }

        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setId(1);
        cartaoCredito.setCard_number(mEdt_numero_cartao.getText().toString().replace(" ", ""));
        cartaoCredito.setExpiry_date(mEdt_data_validade.getText().toString());
        cartaoCredito.setCvv(Integer.valueOf(mEdt_cvv.getText().toString()));

        mRealm.beginTransaction();

        mRealm.copyToRealm(cartaoCredito);

        mRealm.commitTransaction();

        Intent returnItent = new Intent();
        returnItent.putExtra("", "");
        setResult(RESULT_OK, returnItent);
        finish();
    }

    private boolean validar(){
        boolean valido = true;

        String numero_cartao = mEdt_numero_cartao.getText().toString();
        String data_validade = mEdt_data_validade.getText().toString();
        String cvv = mEdt_cvv.getText().toString();

        if(numero_cartao.isEmpty()) {
            mEdt_numero_cartao.setError("Número do Cartão é necessario");
            valido = false;
        }else if(numero_cartao.length() < 19) {
            mEdt_numero_cartao.setError("numero do cartão incorreto");
            valido = false;
        }else{
            mEdt_numero_cartao.setError(null);
        }


        if(data_validade.isEmpty()) {
            mEdt_data_validade.setError("um valor é necessario");
            valido = false;
        }else if(data_validade.length() < 5) {
            mEdt_data_validade.setError("data de validade incorreta");
            valido = false;
        }else if(data_validade.length() == 5){
            String [] partes = mEdt_data_validade.getText().toString().split("/");
            int mes = Integer.valueOf(partes[0]);
            int ano = Integer.valueOf(partes[1]);

            if(mes < 1 || mes > 12 || ano < 1){
                mEdt_data_validade.setError("data de validade incorreta");
                valido = false;
            }
        }else {
            mEdt_data_validade.setError(null);
        }

        if(cvv.isEmpty()) {
            mEdt_cvv.setError("CVV necessario");
            valido = false;
        }else if(numero_cartao.length() < 3) {
            mEdt_cvv.setError("CVV incorreta");
            valido = false;
        }else {
            mEdt_data_validade.setError(null);
        }

        return valido;
    }
}
