package br.com.picpay.picpay.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.model.User;

public class UserViewHolder extends ViewHolderBind<User> {

    private ImageView ivUser;
    private TextView tvName, tvUserName;

    public UserViewHolder(RecyclerView parent) {
        super(parent, R.layout.item_list_user);
        ivUser = itemView.findViewById(R.id.iv_user);
        tvName = itemView.findViewById(R.id.tv_name);
        tvUserName = itemView.findViewById(R.id.tv_user_name);
    }

    @Override
    public void onBindViewHolder(final User model, int position) {
        if (TextUtils.isEmpty(model.getImg())) {
            ivUser.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(itemView.getContext()).load(model.getImg()).into(ivUser, new Callback() {
                @Override
                public void onSuccess() {
                    ivUser.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    ivUser.setVisibility(View.INVISIBLE);
                }
            });
        }
        tvName.setText(model.getName());
        tvUserName.setText(model.getUsername());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(model);
            }
        });
    }
}
