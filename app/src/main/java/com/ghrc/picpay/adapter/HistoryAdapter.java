package com.ghrc.picpay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.model.Transaction;
import com.ghrc.picpay.util.CircleTransform;
import com.ghrc.picpay.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Guilherme on 29/08/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Transaction> mListTrans;
    private LayoutInflater mLayoutInflater;


    public HistoryAdapter(Context context, ArrayList<Transaction> mListTrans) {
        this.context = context;
        this.mListTrans = mListTrans;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryAdapter.MyViewHolder(mLayoutInflater.inflate(R.layout.item_history_trans,parent,false));
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(mListTrans.get(position).getUser_name());
        holder.tvData.setText(DateUtil.dateBR(mListTrans.get(position).getData()));
        holder.tvValue.setText(NumberFormat.getCurrencyInstance().format(mListTrans.get(position).getValue()));
        Picasso.with(context).load(mListTrans.get(position).getImg_user()).transform(new CircleTransform()).into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return mListTrans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvValue;
        TextView tvData;

        MyViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.photo);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvValue = (TextView) itemView.findViewById(R.id.value_trans);
            tvData = (TextView) itemView.findViewById(R.id.data_trans);


        }
    }
}
