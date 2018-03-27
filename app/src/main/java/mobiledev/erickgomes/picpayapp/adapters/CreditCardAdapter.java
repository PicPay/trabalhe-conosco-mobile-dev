package mobiledev.erickgomes.picpayapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import mobiledev.erickgomes.picpayapp.R;
import mobiledev.erickgomes.picpayapp.common.Common;
import mobiledev.erickgomes.picpayapp.models.CreditCard;


public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private List<CreditCard> mAndroidList;
    private Context context;
    private int row_index = -1;

    public CreditCardAdapter(Context context) {
        this.context = context;
        this.mAndroidList = Collections.EMPTY_LIST;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.creditcard_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CreditCard creditCardItem = mAndroidList.get(position);
        holder.cardNumber.setText(String.format("Cartão com final ***%s", creditCardItem.getCard_number().substring(12)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditCardItem.setChecked(true);
                row_index = position; //salvo a posicao do cartao de credito selecionado
                Common.selectedCreditCard = creditCardItem; // envio o cartao de credito selecionado para a classe global
                notifyDataSetChanged();
            }


        });

        if (row_index != position) {
            creditCardItem.setChecked(false); //Ao marcar um cartao de credito, desmarca o anterior marcado
        }

        if (creditCardItem.isChecked()) {
            holder.imgChecked.setImageResource(R.drawable.ic_checked); //marca o V
        } else
            holder.imgChecked.setImageResource(android.R.color.transparent); // desmarca o V


    }


    @Override
    public int getItemCount() {
        return mAndroidList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cardNumber;
        private ImageView imgChecked;

        private ViewHolder(View view) {
            super(view);

            cardNumber = view.findViewById(R.id.tv_credit_card);
            imgChecked = view.findViewById(R.id.img_checked);

        }

    }

    public void setList(List<CreditCard> creditCard) {
        this.mAndroidList = creditCard;
        notifyDataSetChanged();
    }

    public void addItem(CreditCard creditCard) {
        mAndroidList.add(0, creditCard);
        notifyItemInserted(0);
    }

    public List<CreditCard> getList() {
        return mAndroidList;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}

