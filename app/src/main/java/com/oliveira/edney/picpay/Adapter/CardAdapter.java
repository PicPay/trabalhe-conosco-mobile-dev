package com.oliveira.edney.picpay.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.oliveira.edney.picpay.Class.Card;
import com.oliveira.edney.picpay.ItemClickListener;
import com.oliveira.edney.picpay.R;
import java.util.ArrayList;
import java.util.List;

/* Adapter da lista de cartões cadastrados */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private static ItemClickListener clickListener;
    private List<Card> data;
    private int indexCheck= 0;

    public CardAdapter(){
        this.data = new ArrayList<>();
    }

    public void setOnItemClickListener(ItemClickListener clickListener){
        CardAdapter.clickListener = clickListener;
    }

    /* Data de cartões */
    public void setData(List<Card> data){
        this.data = data;
    }

    /* Index do cartão que será marcado como selecionado */
    public void setIndexCheck(int indexCheck){
        this.indexCheck = indexCheck;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cartoes, parent, false);

        return new CardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Card card = data.get(position);

        holder.tvNumero.setText(card.getNumero());

        /* Efeitos de seleção de item */
        if(position == indexCheck){

            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.rlMain.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
        }
        else {

            holder.ivCheck.setVisibility(View.INVISIBLE);
            holder.rlMain.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }
    }

   @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvNumero;
        private RelativeLayout rlMain;
        private ImageView ivCheck;

        ViewHolder(View v){
            super(v);

            v.setOnClickListener(this);

            tvNumero = v.findViewById(R.id.tv_itemCard_numero);
            rlMain = v.findViewById(R.id.rl_itemCard_main);
            ivCheck = v.findViewById(R.id.ic_itemCard_check);
        }

        @Override
        public void onClick(View v) {

            if(clickListener != null){
                clickListener.onItemClick(this.getAdapterPosition());
            }
        }
    }
}