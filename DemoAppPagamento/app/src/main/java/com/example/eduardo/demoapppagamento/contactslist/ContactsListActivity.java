package com.example.eduardo.demoapppagamento.contactslist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    //final List<Contact> contacts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_layout);

        /*setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.textId);
        text.setText("Meu nome e Joao!");
        */

        // Create the presenter
        /*mContactsPresenter = new ContactsListPresenter(
                RepositoryInjection.provideContactsRepository(getApplicationContext()));
        */

        loadContacts();
    }

    // Presenter

    private void loadContacts() {

        ContactsDataSource dataSource = RepositoryInjection.provideContactsRepository(getApplicationContext());

        dataSource.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                setListContacts(contacts);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Todo", "onDataNotAvailable");
            }
        });
    }

    private void setListContacts(List<Contact> contacts) {
        ListView contactsListView = (ListView) findViewById(R.id.contacts_list);
        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts);
        contactsListView.setAdapter(contactsAdapter);
    }


    // View

    class ContactsAdapter extends BaseAdapter {

        private final List<Contact> contacts;
        //private final Context context;


        public ContactsAdapter(List<Contact> values) {
            super();
            contacts = values;
        }

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int i) {
            return contacts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //LayoutInflater inflater = (LayoutInflater) context
            //        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = getLayoutInflater().inflate(R.layout.contact_item, null);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.contact_img);
            TextView nameView = (TextView) rowView.findViewById(R.id.contact_name);
            TextView descriptionView = (TextView) rowView.findViewById(R.id.contact_description);

            //imageView.setImageResource(R.drawable.bigb);
            nameView.setText(contacts.get(i).getName());
            descriptionView.setText(contacts.get(i).getUsername());
            return rowView;
        }
    }
}
