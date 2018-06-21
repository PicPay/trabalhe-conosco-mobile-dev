package com.example.eduardo.demoapppagamento.payment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.eduardo.demoapppagamento.R;
import com.example.eduardo.demoapppagamento.data.Card;

import java.util.List;


public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {

    private CardsListClickListener.Delete mDeleteListener;
    private CardsListClickListener.Select mSelectListener;
    private List<Card> mDataset;
    private static int mLastSelectedPosition;

    public CardsListAdapter(List<Card> cards, CardsListClickListener.Select selectListener,
                            CardsListClickListener.Delete deleteListener) {
        mDataset = cards;
        mDeleteListener = deleteListener;
        mSelectListener = selectListener;

        if (mDataset.size() == 0) {
            mLastSelectedPosition = -1;
        }
        else {
            mLastSelectedPosition = 0;
            mSelectListener.onClick(null, 0);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RadioButton mRadioButtonView;
        public Button mExcludeButtonView;
        private CardsListClickListener.Delete mDeleteListener;
        private CardsListClickListener.Select mSelectListener;

        public ViewHolder(View v, CardsListClickListener.Select selectListener,
                          CardsListClickListener.Delete deleteListener) {
            super(v);
            mRadioButtonView = (RadioButton) v.findViewById(R.id.radio_button);
            mExcludeButtonView = (Button) v.findViewById(R.id.exclude_button);

            mDeleteListener = deleteListener;
            mExcludeButtonView.setOnClickListener(this);

            mSelectListener = selectListener;
            mRadioButtonView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(mExcludeButtonView)) {
                mDeleteListener.onClick(view, getAdapterPosition());
            }
            else {
                mSelectListener.onClick(view, getAdapterPosition());
                mLastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public CardsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        CardsListAdapter.ViewHolder vh = new CardsListAdapter.ViewHolder(v,
                mSelectListener, mDeleteListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(CardsListAdapter.ViewHolder holder, int position) {

        String maskedNum =  applyMaskCardNum(mDataset.get(position).getNumber());
        holder.mRadioButtonView.setText(maskedNum);

        //since only one radio button is allowed to be selected,
        // this condition un-checks previous selections
        holder.mRadioButtonView.setChecked(position == mLastSelectedPosition);
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

        return "**** **** **** " + last4;
    }
}

