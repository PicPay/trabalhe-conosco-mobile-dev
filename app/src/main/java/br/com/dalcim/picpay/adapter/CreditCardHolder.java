package br.com.dalcim.picpay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.CreditCard;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Wiliam
 * @since 03/09/2017
 */

public class CreditCardHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icc_txt_card_number)
    TextView txtCardNumber;

    @BindView(R.id.icc_img_checked)
    ImageView imgChecked;

    public CreditCardHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBindModel(CreditCard creditCard){
        txtCardNumber.setText("Cartão com final " + creditCard.getMaskNumber());
    }

    public void setChecked(boolean checked){
        imgChecked.setVisibility( checked ? View.VISIBLE : View.GONE);
    }
}
