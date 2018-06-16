package com.example.eduardo.demoapppagamento.contactslist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.example.eduardo.demoapppagamento.data.source.ContactsDataSource;
import com.example.eduardo.demoapppagamento.data.source.RepositoryInjection;
import com.example.eduardo.demoapppagamento.util.App;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactsListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_layout);

        mListener = new ContactsListListener();

        mRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ContactsListAdapter(new ArrayList<Contact>(), mListener);
        mRecyclerView.setAdapter(mAdapter);

        loadContacts();
    }



    private void loadContacts() {

        ContactsDataSource dataSource = RepositoryInjection.provideContactsRepository(getApplicationContext());

        dataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                setAdapter(contacts);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Todo", "onDataNotAvailable");
            }
        });
    }

    private void setAdapter(List<Contact> contacts) {
        mAdapter = new ContactsListAdapter(contacts, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }


    public class ContactsListListener implements RecyclerViewClickListener {

        @Override
        public void onClick(View view, int position) {
            Toast.makeText(getBaseContext(), "Position " + position, Toast.LENGTH_SHORT).show();
        }
    }

}
