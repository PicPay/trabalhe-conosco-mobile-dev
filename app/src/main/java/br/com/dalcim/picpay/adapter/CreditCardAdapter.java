package br.com.dalcim.picpay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.CreditCard;


public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardHolder> {
    private final LayoutInflater inflater;
    private final List<CreditCard> creditCards;
    private final View.OnClickListener onClickListener;
    private CreditCard selectedCard;

    public CreditCardAdapter(final RecyclerView recyclerView, final List<CreditCard> creditCards, final CreditCard pSelectedCard, final OnItemClickListener onItemClickListener){
        this.inflater = LayoutInflater.from(recyclerView.getContext());
        this.creditCards = creditCards;
        this.selectedCard = pSelectedCard;

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildLayoutPosition(view);
                selectedCard = creditCards.get(position);
                onItemClickListener.onItemClick(selectedCard);
                CreditCardAdapter.this.notifyDataSetChanged();
            }
        };
    }

    @Override
    public CreditCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CreditCardHolder holder = new CreditCardHolder(inflater.inflate(R.layout.credit_card_item, parent, false));
        holder.itemView.setOnClickListener(onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CreditCardHolder holder, int position) {
        holder.onBindModel(creditCards.get(position));
        holder.setChecked(creditCards.get(position).equals(selectedCard));
    }

    @Override
    public int getItemCount() {
        return creditCards.size();
    }

    public interface OnItemClickListener{
        void onItemClick(CreditCard creditCard);
    }
}
