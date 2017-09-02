package br.com.dalcim.picpay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.User;
import br.com.dalcim.picpay.utils.TrasformationUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 26/08/2017
 */

public class UserHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iuser_txt_name)
    TextView txtName;

    @BindView(R.id.iuser_img_img)
    ImageView imgImg;

    @BindView(R.id.iuser_txt_username)
    TextView txtUsername;

    public UserHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBindModel(User user){
        txtName.setText(user.getName());
        txtUsername.setText(user.getUsername());
        Picasso
                .with(this.itemView.getContext())
                .load(user.getImg())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(TrasformationUtils.circleTransform).into(imgImg);
    }
}
