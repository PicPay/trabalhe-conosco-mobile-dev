package com.example.eduardo.demoapppagamento.new_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;

public class NewCardActivity extends AppCompatActivity implements  View.OnClickListener {

    private TextInputEditText mInputName;
    private TextInputLayout mInputLayoutName;

    private TextInputEditText mInputNum;
    private TextInputLayout mInputLayoutNum;

    private TextInputEditText mInputExpiry;
    private TextInputLayout mInputLayoutExpiry;

    private TextInputEditText mInputCvv;
    private TextInputLayout mInputLayoutCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_layout);
        this.setTitle("Novo Cartão");

        // Set action for add credit card button
        Button addCardButton = (Button) findViewById(R.id.add_card_button);
        addCardButton.setOnClickListener(this);

        mInputName = (TextInputEditText) findViewById(R.id.input_owner_name);
        mInputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_owner_name);

        mInputNum = (TextInputEditText) findViewById(R.id.input_card_num);
        mInputLayoutNum = (TextInputLayout) findViewById(R.id.input_layout_card_num);
        mInputNum.addTextChangedListener(CardInputMask.mask(mInputNum, "#### #### #### ####"));

        mInputExpiry = (TextInputEditText) findViewById(R.id.input_expiry);
        mInputLayoutExpiry = (TextInputLayout) findViewById(R.id.input_layout_expiry);
        mInputExpiry.addTextChangedListener(CardInputMask.mask(mInputExpiry, "##/##"));

        mInputCvv = (TextInputEditText) findViewById(R.id.input_cvv);
        mInputLayoutCvv = (TextInputLayout) findViewById(R.id.input_layout_cvv);
        mInputCvv.addTextChangedListener(CardInputMask.mask(mInputCvv, "###"));
    }

    @Override
    public void onClick(View view) {

        if (!validateCardName()) return;
        if (!validateCardNumber()) return;
        if (!validateCardExpiry()) return;
        if (!validateCardCvv()) return;

        String cardNum;
        int month, year, cvv;

        cardNum = mInputNum.getText().toString().replaceAll(" ","");

        String expiry = mInputExpiry.getText().toString();
        try {
            String[] expiryMonYear = expiry.split("/");
            month = Integer.parseInt(expiryMonYear[0]);
            year = Integer.parseInt(expiryMonYear[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String cvvStr = mInputCvv.getText().toString();
        try {
            cvv = Integer.parseInt(cvvStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        Card newCard = new Card(cardNum, month, year, cvv);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("newCard", newCard);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private boolean validateCardName() {
        if (mInputName.getText().toString().trim().isEmpty()) {
            mInputLayoutName.setError("Prechimento obrigatório");
            mInputName.requestFocus();
            return false;
        } else {
            mInputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCardNumber() {
        String cardNum = mInputNum.getText().toString().replaceAll("[ ]", "");
        if (cardNum.length() < 16) {
            mInputLayoutNum.setError("Numero de dígitos incorreto");
            mInputNum.requestFocus();
            return false;
        } else {
            mInputLayoutNum.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCardExpiry() {

        int month, year;
        String expiryMonYear[];
        String expiry = mInputExpiry.getText().toString();
        try {
            expiryMonYear = expiry.split("/");
        } catch (Exception e) {
            mInputLayoutExpiry.setError("Exemplo: \"05/20\"");
            mInputExpiry.requestFocus();
            return false;
        }

        if (expiryMonYear.length == 2) {

            try {
                month = Integer.parseInt(expiryMonYear[0]);
                year = Integer.parseInt(expiryMonYear[1]);

            } catch (NumberFormatException e) {
                mInputLayoutExpiry.setError("Exemplo: \"05/20\"");
                mInputExpiry.requestFocus();
                return false;
            }

            Toast.makeText(getApplicationContext(), month+"/"+year, Toast.LENGTH_LONG).show();

            if (month > 12 || month < 1) {
                mInputLayoutExpiry.setError("O mês de expiração é inválido");
                mInputExpiry.requestFocus();
                return false;
            }

            if (year < 18) {
                mInputLayoutExpiry.setError("O ano de expiração é inválido");
                mInputExpiry.requestFocus();
                return false;
            }

            mInputLayoutExpiry.setErrorEnabled(false);
            return true;

        }

        mInputLayoutExpiry.setError("Exemplo: \"05/20\"");
        mInputExpiry.requestFocus();
        return false;
    }

    private boolean validateCardCvv() {
        if (mInputCvv.getText().toString().trim().isEmpty()) {
            mInputLayoutCvv.setError("Prechimento obrigatório");
            mInputCvv.requestFocus();
            return false;
        } else {
            mInputLayoutCvv.setErrorEnabled(false);
        }

        return true;
    }

}
