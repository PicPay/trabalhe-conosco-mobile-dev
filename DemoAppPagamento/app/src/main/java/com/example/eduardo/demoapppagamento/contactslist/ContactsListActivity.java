package com.example.eduardo.demoapppagamento.contactslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.CardsDataSource;
import com.example.eduardo.demoapppagamento.data.source.ContactsDataSource;
import com.example.eduardo.demoapppagamento.data.source.RepositoryInjection;
import com.example.eduardo.demoapppagamento.data.source.local.CardsDatabase;
import com.example.eduardo.demoapppagamento.payment.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity implements ContactsListClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> mDataset;
    private ProgressBar mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_layout);
        this.setTitle("Pagar a ...");

        mSpinner = (ProgressBar) findViewById(R.id.contacts_progress_bar);
        mSpinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactsListAdapter(new ArrayList<Contact>(), this);
        mRecyclerView.setAdapter(mAdapter);

        //
        /*CardsDataSource cardsSource = RepositoryInjection.provideCardsRepository(getApplicationContext());
        cardsSource.getCards(new CardsDataSource.LoadCardsCallback() {
            @Override
            public void onCardsLoaded(List<Card> cards) {
                for (Card c: cards) {
                    Log.d("----------->",c.getNumber());
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("----------->","No data");
            }
        });*/

        //Card c0 = new Card("123456",12,18, 321);
        //cardsSource.

        /*CardsDatabase db = CardsDatabase.getInstance(getApplicationContext());
        Card c0 = new Card("123456",12,18, 321);
        db.cardsDao().insertCard(c0);
        List<Card> ls = db.cardsDao().loadAllCards();
        for (Card c: ls) {
            Log.d("Card",c.getNumber());
        }*/
        //finish();

        //

        loadContacts();
    }



    private void loadContacts() {

        ContactsDataSource dataSource = RepositoryInjection.provideContactsRepository(getApplicationContext());

        mSpinner.setVisibility(View.VISIBLE);
        dataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                mSpinner.setVisibility(View.GONE);
                setAdapter(contacts);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Todo", "onDataNotAvailable");

            }
        });
    }

    private void setAdapter(List<Contact> contacts) {
        mDataset = contacts;
        mAdapter = new ContactsListAdapter(contacts, this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View view, int position) {
        Contact c = mDataset.get(position);
        Intent intent = new Intent(ContactsListActivity.this, PaymentActivity.class);
        intent.putExtra("recipient", c);
        startActivity(intent);

        //Toast.makeText(getBaseContext(), "Position " + position, Toast.LENGTH_SHORT).show();

    }
}
