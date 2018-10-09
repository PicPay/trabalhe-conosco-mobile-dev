package br.com.picpay.picpay.view.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.custom.Form;
import br.com.picpay.picpay.custom.TitleEditText;
import br.com.picpay.picpay.custom.ValueTextEditText;
import br.com.picpay.picpay.listerner.TransactionListerner;
import br.com.picpay.picpay.model.Card;
import br.com.picpay.picpay.model.User;
import br.com.picpay.picpay.util.CardUtil;
import io.card.payment.CreditCard;

@SuppressLint("ValidFragment")
public class TransactionFragment extends DialogFragment {

    private User user;
    private Card card;

    private TransactionListerner transactionListerner;

    private ValueTextEditText etValue;

    @SuppressLint("ValidFragment")
    public TransactionFragment(@NonNull User user, @NonNull Card card) {
        this.user = user;
        this.card = card;
    }

    public TransactionFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_transaction, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(User.class.getName(), user);
        outState.putParcelable(Card.class.getName(), card);
        outState.putFloat(ValueTextEditText.class.getName(), etValue.getValue());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        float amount = 0;
        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable(User.class.getName());
            card = savedInstanceState.getParcelable(Card.class.getName());
            amount = savedInstanceState.getFloat(ValueTextEditText.class.getName());
        }
        setLayout();
        etValue.setText(String.valueOf(amount));
    }

    @Nullable
    public TransactionListerner getTransactionListerner() {
        return transactionListerner;
    }

    public void setTransactionListerner(TransactionListerner transactionListerner) {
        this.transactionListerner = transactionListerner;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setLayout() {
        View view = getView();
        if (view != null) {
            final Form form = view.findViewById(R.id.form);

            final ImageView ivUser = view.findViewById(R.id.iv_user);
            TextView tvName = view.findViewById(R.id.tv_name);
            TextView tvUserName = view.findViewById(R.id.tv_user_name);
            Picasso.with(view.getContext()).load(user.getImg()).into(ivUser, new Callback() {
                @Override
                public void onSuccess() {
                    ivUser.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    ivUser.setVisibility(View.INVISIBLE);
                }
            });
            tvName.setText(user.getName());
            tvUserName.setText(user.getUsername());

            CreditCard creditCard = card.getCreditCard();
            view.findViewById(R.id.ll_change_card).setVisibility(View.GONE);
            TextView numberCard = view.findViewById(R.id.number_card);
            ImageView iconCard = view.findViewById(R.id.icon_card);
            TextView nameCard = view.findViewById(R.id.name_card);
            TextView validateCard = view.findViewById(R.id.validate_card);
            numberCard.setText(CardUtil.getNumberFormated(creditCard));
            iconCard.setImageDrawable(CardUtil.getIconCard(creditCard.getCardType(), view.getContext()));
            nameCard.setText(creditCard.cardholderName);
            validateCard.setText(CardUtil.getDateFormated(creditCard));

            etValue = (ValueTextEditText) ((TitleEditText) view.findViewById(R.id.value)).getCustomEditText();

            view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransactionFragment.this.dismissAllowingStateLoss();
                }
            });

            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (form.validate()) {
                        TransactionFragment.this.dismissAllowingStateLoss();
                        if (getTransactionListerner() != null) {
                            getTransactionListerner().execute(user, card, etValue.getValue());
                        }
                    }
                }
            });
        }
    }
}
