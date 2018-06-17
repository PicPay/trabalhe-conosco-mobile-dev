package com.example.eduardo.demoapppagamento.new_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.payment.PaymentActivity;

public class NewCardActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText mInputNum;
    private EditText mInputExpiry;
    private EditText mInputCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_layout);
        this.setTitle("Novo Cartão");

        // Set action for add credit card button
        Button addCardButton = (Button) findViewById(R.id.add_card_button);
        addCardButton.setOnClickListener(this);

        mInputNum = (EditText) findViewById(R.id.input_card_num);
        mInputNum.addTextChangedListener(MaskEditUtil.mask(mInputNum,"#### #### #### ####"));

        mInputExpiry = (EditText) findViewById(R.id.input_expiry);
        mInputExpiry.addTextChangedListener(MaskEditUtil.mask(mInputExpiry,"##/##"));

        mInputCvv = (EditText) findViewById(R.id.input_cvv);
        mInputCvv.addTextChangedListener(MaskEditUtil.mask(mInputCvv, "###"));

    }

    @Override
    public void onClick(View view) {

        Card newCard = checkInputAndReturnCard();
        if (newCard != null) {


            Intent returnIntent = new Intent();
            returnIntent.putExtra("newCard", newCard);
            setResult(RESULT_OK,returnIntent);
            finish();

            //Toast.makeText(getApplicationContext(), "Add Card!", Toast.LENGTH_LONG).show();
        }

    }

    private Card checkInputAndReturnCard() {

        // Card number
        String cardNum = mInputNum.getText().toString().replaceAll("[ ]", "");
        if (cardNum.length() < 16) {
            showError("O número do cartão deve possuír 16 dígitos");
            return null;
        }

        // Card expiry
        int month, year;
        String expiryMonYear[];
        String expiry = mInputExpiry.getText().toString();
        try {
            expiryMonYear = expiry.split("/");
        } catch (Exception e) {
            showError("A data da expiração deve seguir o formato \"00/00\"");
            return null;
        }

        if (expiryMonYear.length == 2) {

            try {
                month = Integer.parseInt(expiryMonYear[0]);
                year = Integer.parseInt(expiryMonYear[1]);
            } catch (NumberFormatException e) {
                showError("A data da expiração deve seguir o formato \"00/00\"");
                return null;
            }

            if (month > 12 || month < 1) {
                showError("O mẽs de expiração " + month + " não é válido");
                return null;
            }

            if (year < 18) {
                showError("O ano de expiração " + year + " não é válido ou o seu cartão está expirado");
                return null;
            }

        }
        else {
            showError("A data da expiração deve seguir o formato \"00/00\"");
            return null;
        }

        int cvv;
        String cvvStr = mInputCvv.getText().toString();
        if (cvvStr.length() > 0) {
           cvv = Integer.parseInt(cvvStr);
        }
        else {
            showError("Número CVV não fornecido");
            return null;
        }

        Card newCard = new Card(cardNum, month, year, cvv);
        return newCard;
    }

    private void showError(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
    }

}
