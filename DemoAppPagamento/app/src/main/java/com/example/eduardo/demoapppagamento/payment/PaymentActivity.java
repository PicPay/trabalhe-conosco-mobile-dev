package com.example.eduardo.demoapppagamento.payment;

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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity implements CardsListClickListener {

    static final int PICK_CARD_REQUEST = 1;
    private Contact mRecipient;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Card> mDataset;
    private int mSelectedItem;

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
        valueEditText.addTextChangedListener(new CurrencyMask(valueEditText));

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


        mRecyclerView = (RecyclerView) findViewById(R.id.credit_cards_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardsListAdapter(new ArrayList<Card>(), this);
        mRecyclerView.setAdapter(mAdapter);

        loadCards();
    }

    private void loadCards() {

        CardsDataSource dataSource = RepositoryInjection.provideCardsRepository(getApplicationContext());

        dataSource.getCards(new CardsDataSource.LoadCardsCallback() {
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
        mAdapter = new CardsListAdapter(cards, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        mSelectedItem = position;
        Log.d("CLICK", "Position "+ position);
        //Contact c = mDataset.get(position);
        //Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
        //intent.putExtra("recipient", c);
        //startActivity(intent);

        //Toast.makeText(getBaseContext(), "Position " + position, Toast.LENGTH_SHORT).show();

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

    class CurrencyMask implements TextWatcher {

        boolean ignoreChange = false;
        final EditText editText;

        public CurrencyMask(EditText editText) {
            super();
            this.editText = editText;
        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            if (!ignoreChange) {
                String string = s.toString();
                string = string.replace(".", "");
                string = string.replace(" ", "");
                if (string.length() == 0)
                    string = ".     ";
                else if (string.length() == 1)
                    string = ".  " + string;
                else if (string.length() == 2)
                    string = "." + string;
                else if (string.length() > 2)
                    string = string.substring(0, string.length() - 2) + "." + string.substring(string.length() - 2, string.length());
                ignoreChange = true;
                editText.setText(string);
                editText.setSelection(editText.getText().length());
                ignoreChange = false;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
