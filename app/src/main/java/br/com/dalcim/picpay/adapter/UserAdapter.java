package br.com.dalcim.picpay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.User;

/**
 * @author Wiliam
 * @since 26/08/2017
 */

public class UserAdapter extends RecyclerView.Adapter<UserHolder>{
    private final LayoutInflater inflater;
    private final List<User> users;

    private final View.OnClickListener onClickListener;

    public UserAdapter(final RecyclerView recyclerView, final List<User> users, final OnItemClickListener onItemClickListener){
        this.inflater = LayoutInflater.from(recyclerView.getContext());
        this.users = users;

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(users.get(recyclerView.getChildLayoutPosition(view)));
            }
        };
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserHolder userHolder = new UserHolder(inflater.inflate(R.layout.user_item, parent, false));
        userHolder.itemView.setOnClickListener(onClickListener);
        return userHolder;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.onBindModel(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnItemClickListener{
        void onItemClick(User user);
    }
}
