package br.com.everaldocardosodearaujo.picpay.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.everaldocardosodearaujo.picpay.Activity.CreditCardActivity;
import br.com.everaldocardosodearaujo.picpay.Activity.TransactionActivity;
import br.com.everaldocardosodearaujo.picpay.App.FunctionsApp;
import br.com.everaldocardosodearaujo.picpay.Object.UserObject;
import br.com.everaldocardosodearaujo.picpay.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static br.com.everaldocardosodearaujo.picpay.App.SessionApp.TB_CREDIT_CARD;

/**
 * Created by E. Cardoso de Araújo on 15/03/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    private Context context;
    private List<UserObject> lstUsers;
    private LayoutInflater layoutInflater;
    private float scale;
    private int width;
    private int height;
    private FunctionsApp.RecyclerViewTouchListener.RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public UsersAdapter(Context context, List<UserObject> lstUsers){
        this.context = context;
        this.lstUsers = lstUsers;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scale = this.context.getResources().getDisplayMetrics().density;
        this.width = this.context.getResources().getDisplayMetrics().widthPixels - (int)(14 * this.scale + 0.5f);
        this.height = (this.width / 16) * 9;
    }

    public void setRecyclerViewOnClickListenerHack(FunctionsApp.RecyclerViewTouchListener.RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(UserObject User){
        this.lstUsers.add(User);
        notifyItemInserted(lstUsers.size());
    }

    public void removeListItem(int position){
        this.lstUsers.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(this.layoutInflater.inflate(R.layout.layout_card_users, parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final UserObject user = this.lstUsers.get(position);
        if (holder.itemView.getTag() == null){
            holder.itemView.setTag(user);
            holder.idName.setText(user.getName());
            holder.idUserName.setText(user.getUsername());
            if (!user.getImg().equals("")){
                Picasso.with(context).load(user.getImg()).into(holder.idImg);
                holder.idImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    if (TB_CREDIT_CARD.select().get(0) != null){
                        Bundle param = new Bundle();
                        param.putString("img",user.getImg());
                        param.putString("name",user.getName());
                        param.putString("username",user.getUsername());
                        FunctionsApp.startActivity(context, TransactionActivity.class,param);
                    }else{
                        FunctionsApp.startActivity(context, CreditCardActivity.class,null);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstUsers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CircleImageView idImg;
        public TextView idUserName;
        public TextView idName;
        public RecyclerView idRvUsers;

        public MyViewHolder(View view){
            super(view);
            idImg = (CircleImageView) view.findViewById(R.id.idImg);
            idUserName = (TextView) view.findViewById(R.id.idUserName);
            idName = (TextView) view.findViewById(R.id.idName);
            idRvUsers = (RecyclerView) view.findViewById(R.id.idRvUsers);
        }

        @Override
        public void onClick(View view) {
            if(recyclerViewOnClickListenerHack != null){
                recyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }
    }
}
