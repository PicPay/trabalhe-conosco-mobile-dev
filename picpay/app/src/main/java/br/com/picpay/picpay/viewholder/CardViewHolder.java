package br.com.picpay.picpay.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.ChangeCard;
import br.com.picpay.picpay.model.DeleteCard;
import br.com.picpay.picpay.util.CardUtil;
import io.card.payment.CreditCard;

public class CardViewHolder extends ViewHolderBind<Card> {

    private TextView numberCard, nameCard, validateCard;
    private ImageView iconCard;

    public CardViewHolder(RecyclerView parent) {
        super(parent, R.layout.item_list_card);
        numberCard = itemView.findViewById(R.id.number_card);
        iconCard = itemView.findViewById(R.id.icon_card);
        nameCard = itemView.findViewById(R.id.name_card);
        validateCard = itemView.findViewById(R.id.validate_card);
    }

    @Override
    public void onBindViewHolder(final Card card, int position) {
        CreditCard creditCard = card.getCreditCard();
        numberCard.setText(CardUtil.getNumberFormated(creditCard));
        iconCard.setImageDrawable(CardUtil.getIconCard(creditCard.getCardType(), itemView.getContext()));
        nameCard.setText(creditCard.cardholderName);
        validateCard.setText(CardUtil.getDateFormated(creditCard));
        itemView.findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCard changeCard = new ChangeCard();
                changeCard.setCard(card);
                EventBus.getDefault().post(changeCard);
            }
        });
        itemView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCard deleteCard = new DeleteCard();
                deleteCard.setCard(card);
                EventBus.getDefault().post(deleteCard);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(card);
            }
        });
    }
}