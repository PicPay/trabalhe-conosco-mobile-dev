package br.com.picpay.picpay.view.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    @SuppressLint("ValidFragment")
    public TransactionFragment(@NonNull User user, @NonNull Card card, @NonNull TransactionListerner transactionListerner) {
        this.user = user;
        this.card = card;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_transaction, container, false);

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

        final ValueTextEditText etValue = (ValueTextEditText) ((TitleEditText) view.findViewById(R.id.value)).getCustomEditText();

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
                    transactionListerner.execute(user, card, etValue.getValue());
                }
            }
        });

        return view;
    }
}
