package br.com.picpay.andbar.testepicpay;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.picpay.andbar.testepicpay.base.DatabaseHandler;

/**
 * Created by andba on 22/04/2018.
 */

public class CadastraCartao extends AppCompatActivity
{
    EditText txtNumeroCartao;
    EditText txtCVV;
    EditText txtValidade;

    Button btnOK;
    Button btnCancelar;

    DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_cartao);

        db = new DatabaseHandler(this);

        //region Mapeamento de itens da tela

        txtNumeroCartao = (EditText)findViewById(R.id.txbNumeroCartao);
        txtCVV = (EditText)findViewById(R.id.txbCVV);
        txtValidade = (EditText)findViewById(R.id.txbValidade);

        btnOK = (Button)findViewById(R.id.btnCadastrar);
        btnCancelar = (Button)findViewById(R.id.btnProcurar);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar(view);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancelar(view);
            }
        });

        //endregion
    }

    private void Gravar(View view)
    {

        CartaoUsuario novoCartao = new CartaoUsuario();
        novoCartao.NumeroCartao = txtNumeroCartao.getText().toString();
        novoCartao.CodigoValidacao = txtCVV.getText().toString();
        novoCartao.ValidadeCartao = txtValidade.getText().toString();

        if(!db.addCartao(novoCartao,DatabaseHandler.TABLE_CARDS))
        {
            Toast.makeText(this,"Falha ao inserir cartão", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Cartão inserido co sucesso!", Toast.LENGTH_LONG).show();
            Cancelar(view);
        }

    }

    private void Cancelar(View view)
    {
        finish();
    }






}
