package project.picpay.test.home.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import project.picpay.test.R;
import project.picpay.test.databinding.ItemListUserBinding;
import project.picpay.test.home.model.UserModel;

/**
 * Created by Rodrigo Oliveira on 16/08/2018 for sac-digital-importacao.
 * ContactModel us rodrigooliveira.tecinfo@gmail.com
 */
public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.RecyclerViewHolder> {

    private List<UserModel> itemModelList;
    private LayoutInflater layoutInflater;
    private final OnActionListerner mActionListener;

    ListUserAdapter(List<UserModel> borrowModelList, OnActionListerner actionListerner) {
        setHasStableIds(true);
        this.itemModelList = borrowModelList;
        this.mActionListener = actionListerner;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListUserBinding binding = DataBindingUtil.inflate(validInflater(parent), R.layout.item_list_user, parent, false);
        return new ListUserAdapter.RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
        holder.binding.setPost(itemModelList.get(position));
        holder.itemView.setOnClickListener(view -> mActionListener.onSelectUser(itemModelList.get(position)));

    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    @Override
    public long getItemId(int position) {
        return itemModelList.get(position).getId();
    }

    public void addItems(List<UserModel> borrowModelList) {
        this.itemModelList = borrowModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ItemListUserBinding binding;

        RecyclerViewHolder(ItemListUserBinding view) {
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
        void onSelectUser(UserModel user);
    }

}