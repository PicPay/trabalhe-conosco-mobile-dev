package com.ghrc.picpay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.model.CreditCard;
import com.ghrc.picpay.util.ButtonHackClick;

import java.util.ArrayList;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.MyViewHolder> {
    private ArrayList<CreditCard> mListCard;
    private LayoutInflater mLayoutInflater;
    private ButtonHackClick mButtonHack;
    public CreditCardAdapter(Context context, ArrayList<CreditCard> mListCard,ButtonHackClick b) {
        this.mListCard = mListCard;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mButtonHack  = b;
    }

    public void updateList(ArrayList<CreditCard> newCards) {
        mListCard.clear();
        mListCard.addAll(newCards);
        this.notifyDataSetChanged();
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
        Button btnDeleteCard;

        MyViewHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.number_card);
            tvExpiryDate = (TextView) itemView.findViewById(R.id.id_expiry_date);
            btnDeleteCard = (Button) itemView.findViewById(R.id.btnDeleteCard);
            btnDeleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonHack.onClickListener(v,getAdapterPosition());
                }
            });
        }
    }
}
