package com.example.eduardo.demoapppagamento.contactslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;

import java.util.List;

class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {
    private List<Contact> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDescriptionView;

        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.contact_img);
            mNameView = (TextView) v.findViewById(R.id.contact_name);
            mDescriptionView = (TextView) v.findViewById(R.id.contact_description);
        }
    }

    public ContactsListAdapter(List<Contact> contacts) {
        mDataset = contacts;
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.mImageView.setImageResource(R.drawable.bigb);
        holder.mNameView.setText(mDataset.get(position).getName());
        holder.mDescriptionView.setText(mDataset.get(position).getUsername());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
