package com.ghrc.picpay.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghrc.picpay.R;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.util.BD;
import com.ghrc.picpay.util.CreditCardExpiryInputFilter;
import com.ghrc.picpay.util.CreditCardNumberFormattingTextWatcher;

public class RegisterCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adicionando Cartão");
        final EditText edtNumberCard = (EditText)findViewById(R.id.edt_numbercard);
        edtNumberCard.addTextChangedListener( new CreditCardNumberFormattingTextWatcher());
        final EditText edtExpiryDate = (EditText)findViewById(R.id.edt_expiry);
        final EditText edtCVV = (EditText) findViewById(R.id.edt_cvv);
        final TextInputLayout txtNumberCard = (TextInputLayout) findViewById(R.id.edt_numbercard_input);
        final TextInputLayout txtCVV = (TextInputLayout) findViewById(R.id.edt_cvv_input);
        final TextInputLayout txtExpiryDate = (TextInputLayout) findViewById(R.id.edt_expiry_input);
        edtExpiryDate.setFilters( new InputFilter[]{new CreditCardExpiryInputFilter()});
        Button btnRegisterCard = (Button) findViewById(R.id.btnRegisterCard);
        btnRegisterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setError(edtNumberCard,txtNumberCard,"Informe o número do cartão")){
                    return;
                }
                if(setError(edtExpiryDate,txtExpiryDate,"Informe o Mês/Ano")){
                    return;
                }
                if(setError(edtCVV,txtCVV,"Informe o CVV")){
                    return;
                }
                CreditCard c = new CreditCard("0",edtNumberCard.getText().toString(),edtCVV.getText().toString(),edtExpiryDate.getText().toString());
                BD bd = new BD(RegisterCardActivity.this);
                bd.insertCreditCard(c);
                Toast.makeText(RegisterCardActivity.this, "Cartão registrado com sucesso.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean setError(EditText edit,TextInputLayout textInputLayout,String hint)
    {
        if(edit.getText().length() == 0)
        {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(hint);
            return true;
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
            return false;
        }
    }


}
