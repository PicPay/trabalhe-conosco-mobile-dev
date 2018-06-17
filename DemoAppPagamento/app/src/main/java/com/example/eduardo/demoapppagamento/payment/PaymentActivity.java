package com.example.eduardo.demoapppagamento.payment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;

public class PaymentActivity extends AppCompatActivity {
    private Contact mRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        setTitle("Transferência");

        Intent intent = getIntent();
        if (intent.hasExtra("recipient")){
            mRecipient = (Contact) intent.getSerializableExtra("recipient");
        }

        TextView recipientName = (TextView) findViewById(R.id.recipient_payment);
        recipientName.setText(mRecipient.getName());

    }
}
