package com.example.eduardo.demoapppagamento.payment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;

import java.util.List;


public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {

    private CardsListClickListener mListener;
    private List<Card> mDataset;
    private int mSelectedItem;

    public CardsListAdapter(List<Card> cards, CardsListClickListener listener) {
        mDataset = cards;
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {// implements View.OnClickListener {

        public RadioButton mRadioButtonView;
        public Button mExcludeButtonView;
        private CardsListClickListener mListener;

        public ViewHolder(View v, CardsListClickListener listener) {
            super(v);
            mRadioButtonView = (RadioButton) v.findViewById(R.id.radio_button);
            mExcludeButtonView = (Button) v.findViewById(R.id.exclude_button);
            mListener = listener;
            //v.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }*/
    }

    @Override
    public CardsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        CardsListAdapter.ViewHolder vh = new CardsListAdapter.ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardsListAdapter.ViewHolder holder, int position) {

        String maskedNum =  applyMaskCardNum(mDataset.get(position).getNumber());
        holder.mRadioButtonView.setText(maskedNum);


    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private String applyMaskCardNum(String num) {
        int numLen = num.length();
        String last4;
        if (num == null || numLen <= 4) {
            last4 = num;
        } else {
            last4 = num.substring(numLen - 4);
        }

        return "xxxx xxxx xxxx " + last4;
    }
}

