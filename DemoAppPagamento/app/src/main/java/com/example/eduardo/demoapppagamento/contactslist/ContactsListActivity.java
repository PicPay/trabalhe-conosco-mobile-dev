package com.example.eduardo.demoapppagamento.contactslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.ContactsDataSource;
import com.example.eduardo.demoapppagamento.data.source.RepositoryInjection;
import com.example.eduardo.demoapppagamento.payment.PaymentActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity implements ContactsListClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Contact> mDataset;
    private ProgressBar mSpinner;
    private LinearLayout mErrorLayout;
    private Button mTryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_layout);
        this.setTitle("Contatos");

        mSpinner = (ProgressBar) findViewById(R.id.contacts_progress_bar);
        mSpinner.setVisibility(View.GONE);

        mErrorLayout = (LinearLayout) findViewById(R.id.contacts_error_layout);
        mErrorLayout.setVisibility(LinearLayout.INVISIBLE);

        mTryAgainButton = (Button) findViewById(R.id.contacts_try_again_button);
        setTryAgainCallback(mTryAgainButton);

        mRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactsListAdapter(new ArrayList<Contact>(), this);
        mRecyclerView.setAdapter(mAdapter);

        loadContacts();
    }

    private void loadContacts() {

        ContactsDataSource dataSource = RepositoryInjection.provideContactsRepository(getApplicationContext());

        mSpinner.setVisibility(View.VISIBLE);
        dataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                mSpinner.setVisibility(View.INVISIBLE);
                setAdapter(contacts);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Todo", "onDataNotAvailable");
                mSpinner.setVisibility(View.INVISIBLE);
                mErrorLayout.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }

    private void setTryAgainCallback(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Tentando novamente", Toast.LENGTH_SHORT).show();
                mErrorLayout.setVisibility(LinearLayout.INVISIBLE);
                loadContacts();
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
    }
}
