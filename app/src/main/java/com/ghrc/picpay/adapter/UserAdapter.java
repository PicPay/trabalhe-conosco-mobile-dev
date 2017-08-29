package com.ghrc.picpay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghrc.picpay.R;
import com.ghrc.picpay.model.User;
import com.ghrc.picpay.util.ButtonHackClick;
import com.ghrc.picpay.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Guilherme on 27/08/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<User> mListUser;
    private ArrayList<User> mFilteredList;
    private LayoutInflater mLayoutInflater;
    private ButtonHackClick button;

    public UserAdapter(Context context, ArrayList<User> mListUser, ButtonHackClick b) {
        this.context = context;
        this.mListUser = mListUser;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.button = b;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item_user,parent,false));
    }

    public void addListItem(User u, int position)
    {
        mListUser.add(u);
        notifyItemInserted(position);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(mListUser.get(position).getName() +  " | " + mListUser.get(position).getUsername());
        holder.tvIdUser.setText(mListUser.get(position).getId());
        Picasso.with(context).load(mListUser.get(position).getImg()).transform(new CircleTransform()).into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mListUser;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User user : mListUser) {
                        if (user.getUsername().contains(charSequence) || user.getName().contains(charSequence)) {
                            filteredList.add(user);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvIdUser;
        Button btnEnviarPag;

        MyViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.photo);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvIdUser = (TextView) itemView.findViewById(R.id.id_user);
            btnEnviarPag = (Button) itemView.findViewById(R.id.btnSendPag);
            btnEnviarPag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.onClickListener(v, getLayoutPosition());
                }
            });
        }
    }

}
