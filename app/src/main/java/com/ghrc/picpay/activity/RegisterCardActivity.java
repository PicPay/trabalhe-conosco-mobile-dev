package com.ghrc.picpay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.ghrc.picpay.R;
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
        EditText edtNumberCard = (EditText) findViewById(R.id.edt_numbercard);
        edtNumberCard.addTextChangedListener(new CreditCardNumberFormattingTextWatcher());
        final EditText edtExpiryDate = (EditText) findViewById(R.id.edt_expiry);
    }
}
