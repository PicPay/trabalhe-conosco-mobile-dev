package com.tmontovaneli.picpaychallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.tmontovaneli.picpaychallenge.dao.CartaoDAO;
import com.tmontovaneli.picpaychallenge.model.Cartao;
import com.tmontovaneli.picpaychallenge.util.DataHelper;
import com.tmontovaneli.picpaychallenge.util.StringHelper;

import java.util.Date;

public class CartaoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao);

        setTitle("Cartão");

        final AQuery aQuery = new AQuery(this);

        aQuery.id(R.id.btn_confirmar).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit = aQuery.id(R.id.edittext_numero_cartao).getEditText();
                String temp = edit.getText().toString();

                Cartao cartao = new Cartao();

                boolean erro = false;
                if (StringHelper.isEmpty(temp)) {
                    edit.setError("Obrigatório");
                    erro = true;
                } else {
                    cartao.setNumero(temp);
                }

                edit = aQuery.id(R.id.edittext_cvv).getEditText();
                temp = edit.getText().toString();
                if (StringHelper.isEmpty(temp)) {
                    edit.setError("Obrigatório");
                    erro = true;
                } else {
                    cartao.setCvv(temp);
                }

                edit = aQuery.id(R.id.data_expiracao).getEditText();
                temp = edit.getText().toString();
                if (StringHelper.isEmpty(temp)) {
                    edit.setError("Obrigatório");
                    erro = true;
                } else {
                    Date date = DataHelper.parseMMyyyy(temp);
                    if (date == null) {
                        edit.setError("Data inválida");
                        erro = true;
                    } else {
                        cartao.setExpiracao(date);
                    }
                }

                if (!erro) {
                    CartaoDAO cartaoDAO = new CartaoDAO(CartaoActivity.this);
                    cartaoDAO.insere(cartao);
                    CartaoActivity.this.finish();
                }
            }
        });

    }
}
