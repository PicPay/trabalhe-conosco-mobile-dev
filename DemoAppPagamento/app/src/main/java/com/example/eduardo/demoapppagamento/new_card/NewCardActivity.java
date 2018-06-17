package com.example.eduardo.demoapppagamento.new_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.payment.PaymentActivity;

public class NewCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_layout);
        this.setTitle("Novo Cartão");


        // Set action for add credit card button
        Button addCardButton = (Button) findViewById(R.id.add_card_button);
        addCardButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("card","abc");
                setResult(RESULT_OK,returnIntent);
                finish();

                //Toast.makeText(getApplicationContext(), "Add Card!", Toast.LENGTH_LONG).show();

            }
        });
    }

}
