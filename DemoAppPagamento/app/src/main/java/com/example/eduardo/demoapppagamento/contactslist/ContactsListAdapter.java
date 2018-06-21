package com.example.eduardo.demoapppagamento.contactslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Contact;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private ContactsListClickListener mListener;
    private List<Contact> mDataset;

    public ContactsListAdapter(List<Contact> contacts, ContactsListClickListener listener) {
        mDataset = contacts;
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SimpleDraweeView mImageView;
        public TextView mNameView;
        public TextView mDescriptionView;
        private ContactsListClickListener mListener;

        public ViewHolder(View v, ContactsListClickListener listener) {
            super(v);
            mImageView = (SimpleDraweeView) v.findViewById(R.id.contact_img);
            mNameView = (TextView) v.findViewById(R.id.contact_name);
            mDescriptionView = (TextView) v.findViewById(R.id.contact_description);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact c = mDataset.get(position);
        holder.mNameView.setText(c.getName());
        holder.mDescriptionView.setText(c.getUsername());
        holder.mImageView.setImageURI(c.getImg()); // Get thumbnails by img url
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
