package project.picpay.test.creditcard.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import project.picpay.test.R;
import project.picpay.test.creditcard.model.CreditCardModel;
import project.picpay.test.databinding.ItemListCardBinding;

/**
 * Created by Rodrigo Oliveira on 16/08/2018 for sac-digital-importacao.
 * ContactModel us rodrigooliveira.tecinfo@gmail.com
 */
public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.RecyclerViewHolder> {

    private LayoutInflater layoutInflater;
    private List<CreditCardModel> itemModelList;
    private final OnActionListerner mActionListener;

    public ListCardAdapter(List<CreditCardModel> borrowModelList, OnActionListerner actionListerner) {
        setHasStableIds(true);
        this.itemModelList = borrowModelList;
        this.mActionListener = actionListerner;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListCardBinding binding = DataBindingUtil.inflate(validInflater(parent), R.layout.item_list_card, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        holder.binding.setPost(itemModelList.get(position));
        holder.itemView.setOnClickListener(view -> mActionListener.onGetCreditCard(itemModelList.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    @Override
    public long getItemId(int position) {
        return itemModelList.get(position).getId();
    }


    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ItemListCardBinding binding;

        RecyclerViewHolder(ItemListCardBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }

    private LayoutInflater validInflater(ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return layoutInflater;
    }

    public interface OnActionListerner {
        void onGetCreditCard(CreditCardModel card);
    }

}