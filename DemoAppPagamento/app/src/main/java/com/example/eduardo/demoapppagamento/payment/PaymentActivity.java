package com.example.eduardo.demoapppagamento.payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.CardsDataSource;
import com.example.eduardo.demoapppagamento.data.source.RepositoryInjection;
import com.example.eduardo.demoapppagamento.new_card.NewCardActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    static final int PICK_CARD_REQUEST = 1;
    private Contact mRecipient;

    CardsDataSource mDataSource;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Card> mDataset;
    private int mSelectedItem;

    private CardsListClickListener.Delete mDeleteCardCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        setTitle("Transferência");

        Intent intent = getIntent();
        if (intent.hasExtra("recipient")){
            mRecipient = (Contact) intent.getSerializableExtra("recipient");
        }

        // Set recipient name
        TextView recipientName = (TextView) findViewById(R.id.recipient_payment);
        recipientName.setText(mRecipient.getName());

        // Set number mask for currency value
        EditText valueEditText =  (EditText) findViewById(R.id.value_payment);
        //valueEditText.addTextChangedListener(new CurrencyMask(valueEditText));

        // Set action for add credit card button
        Button addCardButton = (Button) findViewById(R.id.add_new_card_button);
        addCardButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Add Card!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PaymentActivity.this, NewCardActivity.class);
                startActivityForResult(intent, PICK_CARD_REQUEST);
            }
        });

        // Initialize a CardsDataSource
        mDataSource = RepositoryInjection.provideCardsRepository(getApplicationContext());

        // Initialize callback implementation to delete cards
        mDeleteCardCallback = new DeleteCardOnList();

        mRecyclerView = (RecyclerView) findViewById(R.id.credit_cards_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardsListAdapter(new ArrayList<Card>(), mDeleteCardCallback);
        mRecyclerView.setAdapter(mAdapter);

        setPaymentButton();
        loadCards();
    }

    private void setPaymentButton() {
        Button paymentButton = (Button) findViewById(R.id.confirm_payment_button);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText valueEditText = (EditText) findViewById(R.id.value_payment);
                String value = valueEditText.getText().toString();
                if (value.length() > 1) {
                    double parsed = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#.00");
                    value = df.format(parsed);
                    setPaymentConfirmationDialog(value);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Valor da transferência não foi definido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setPaymentConfirmationDialog(String value) {
        // Create AlertDialog to confirm or decline payment
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle("Confirmar transferência de R$ "+ value + " para "+ mRecipient.getName() +"?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // if post success
                Toast.makeText(getApplicationContext(), "Transferência efetuada", Toast.LENGTH_LONG).show();
                finish();

                // if rejected
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadCards() {
        mDataSource.getCards(new CardsDataSource.LoadCardsCallback() {
            @Override
            public void onCardsLoaded(List<Card> cards) {
                setAdapter(cards);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Todo", "onDataNotAvailable");
            }
        });
    }

    private void setAdapter(List<Card> cards) {
        mDataset = cards;
        mAdapter = new CardsListAdapter(cards, mDeleteCardCallback);
        mRecyclerView.setAdapter(mAdapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Get new card added in NewCardActiviry, save in database and reload list
        if (requestCode == PICK_CARD_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("newCard")){
                    Card newCard = (Card) data.getSerializableExtra("newCard");
                    Toast.makeText(getBaseContext(), newCard.getNumber(), Toast.LENGTH_SHORT).show();

                    CardsDataSource cardsSource = RepositoryInjection.provideCardsRepository(getApplicationContext());
                    cardsSource.saveCard(newCard);
                    loadCards();
                }
            }
        }
    }

    class DeleteCardOnList implements CardsListClickListener.Delete {

        // Callback to delete a card on the list
        @Override
        public void onClick(View view, int position) {

            final Card card = mDataset.get(position);
            String cardNumber = card.getNumber();
            String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);

            // Create AlertDialog to confirm or decline deletion
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
            builder.setTitle("Excuir cartão terminado em \""+lastFourDigits+"\" ?");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    mDataSource.deleteCard(card);
                    loadCards();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
