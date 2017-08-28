package com.ghrc.picpay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.model.CreditCard;

import java.util.ArrayList;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.MyViewHolder> {
    private ArrayList<CreditCard> mListCard;
    private LayoutInflater mLayoutInflater;

    public CreditCardAdapter(Context context, ArrayList<CreditCard> mListCard) {
        this.mListCard = mListCard;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreditCardAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_credit_card,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNumber.setText("XXXX XXXX XXXX "+mListCard.get(position).getCard_number().substring(mListCard.get(position).getCard_number().length() -4));
        holder.tvExpiryDate.setText("Validade " +mListCard.get(position).getExpiry_date());
    }

    @Override
    public int getItemCount() {
        return mListCard.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvExpiryDate;

        MyViewHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.number_card);
            tvExpiryDate = (TextView) itemView.findViewById(R.id.id_expiry_date);

        }
    }
}
