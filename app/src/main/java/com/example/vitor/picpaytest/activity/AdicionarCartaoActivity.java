package com.example.vitor.picpaytest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vitor.picpaytest.R;
import com.example.vitor.picpaytest.dominio.Cartao;
import com.example.vitor.picpaytest.persistencia.PicPayDB;

import java.util.Date;

public class AdicionarCartaoActivity extends AppCompatActivity {

    EditText numero, cvv, vencimento;
    Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cartao);

        setupToolbar();
        setupComponents();

        vencimento.setOnKeyListener(new DateTimeMask());

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date d = new Date();

                int mes = Integer.parseInt(vencimento.getText().toString().substring(0, 2));
                int ano = Integer.parseInt(vencimento.getText().toString().substring(3));
                int anoAtual = Integer.parseInt(String.valueOf(d.getYear()).toString().substring(1));

                if(numero.getText().length() < 16){
                    Toast.makeText(AdicionarCartaoActivity.this, "Número do Cartão Inválido", Toast.LENGTH_LONG).show();
                }else if(vencimento.getText().length() < 5 || mes > 12 || mes < 1 || (mes > d.getMonth()+1 && ano <= anoAtual )){
                    Toast.makeText(AdicionarCartaoActivity.this, "Mes e Ano Inválidos", Toast.LENGTH_LONG).show();
                }else{
                    Cartao cartao = new Cartao();
                    cartao.setNumero(numero.getText().toString());
                    cartao.setVencimento(vencimento.getText().toString());
                    cartao.setCvv(Integer.parseInt(cvv.getText().toString()));
                    salvarCartao(AdicionarCartaoActivity.this, cartao);
                    finish();
                }


            }
        });

    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Adicionar Cartão");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupComponents(){
        numero = (EditText) findViewById(R.id.numero_cartao_adicionarCartao);
        cvv = (EditText) findViewById(R.id.cvv_adicionarCartao);
        vencimento = (EditText) findViewById(R.id.vencimento_adicionarCartao);
        adicionar = (Button) findViewById(R.id.add_cartao_adicionarCartao);
    }

    private void salvarCartao(Context context, Cartao cartao) {
        PicPayDB db = new PicPayDB(context);

        try{
            db.salvaCartao(cartao);
        }finally {
            db.close();
        }
    }

    class DateTimeMask implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            EditText ed = (EditText) v;

            if (event.getAction() == KeyEvent.ACTION_UP
                    && keyCode != KeyEvent.KEYCODE_DEL) {

                int length = ed.getText().toString().length();

                if(length == 2){
                    ed.setTextKeepState(ed.getText() + "/");
                }
            }

            Selection.setSelection(ed.getText(), ed.getText().toString()
                    .length());
            return false;
        }
    }

}
