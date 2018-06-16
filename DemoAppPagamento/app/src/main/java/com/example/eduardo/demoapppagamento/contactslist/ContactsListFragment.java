package com.example.eduardo.demoapppagamento.contactslist;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;

import java.util.List;

public class ContactsListFragment extends Fragment implements ContactsListContract.View {

    private ContactsListContract.Presenter mContactsPresenter;

    private ListView mContactsListView;

    public ContactsListFragment(){

    }

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showContacts(List<Contact> contacts) {

    }

    @Override
    public void setPresenter(ContactsListContract.Presenter presenter) {
        mContactsPresenter = presenter;
    }

   /* private void setListContacts(List<Contact> contacts) {
        ListView contactsListView = (ListView) findViewById(R.id.contacts_list);
        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts);
        contactsListView.setAdapter(contactsAdapter);
    }

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
*/

}
