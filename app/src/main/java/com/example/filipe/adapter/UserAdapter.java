package com.example.filipe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.filipe.enviadinheiro.R;
import com.example.filipe.interfaces.RecyclerViewOnClickListenerHack;
import com.example.filipe.model.User;
import com.example.filipe.util.CircleTransform;

import java.util.List;

/**
 * Created by filipe on 23/04/18.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<User> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public UserAdapter(Context c, List<User> l){
        context = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_user, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {

        User user = mList.get(position);

        holder.txt_username.setText(user.getUsername());
        holder.txt_name.setText(user.getName());

        Glide.with(context).load(user.getImg())
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(context))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.imv_img);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void setDados(List<User> l){
        mList = l;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_username;
        public TextView txt_name;
        public ImageView imv_img;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_username = (TextView) itemView.findViewById(R.id.txt_username);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            imv_img = (ImageView) itemView.findViewById(R.id.imv_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(view, getLayoutPosition());
            }
        }
    }
}
