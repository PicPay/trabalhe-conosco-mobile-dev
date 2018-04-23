package gilianmarques.dev.picpay_test.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.activities.AddCreditCard;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.DialogSelectCreditCard;
import gilianmarques.dev.picpay_test.utils.ProfilePicHolder;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {

    private static final int ADD_CARD_REQ_CODE = 1, ADD_CARD_REQ_CODE_FROM_DIALOG = 2;
    private TransactionCallback callback;
    private Contact mContact;
    private View rootView;
    private EditText edtAmount;
    private String currencySimbol, amount = "";
    private RelativeLayout container;
    private Button payButton;
    private Activity mActivity;
    private ArrayList<CreditCard> mCards = new ArrayList<>();
    private TextView tvCreditCardInfo;
    private CreditCard choosedCreditCard;
    DialogSelectCreditCard mDialogSelectCreditCard;

    /**
     * @param callback c
     * @return r
     * @see ContactsListFragment para uma descrição
     */
    public PaymentFragment attachCallback(TransactionCallback callback, Activity mActivity) {
        this.callback = callback;
        /*Fragment.getActivity() as vezes retorna null*/
        this.mActivity = mActivity;
        return this;
    }

    public PaymentFragment newInstance(Contact mContact) {
        PaymentFragment mPaymentFragment = new PaymentFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("contact", mContact);
        mPaymentFragment.setArguments(mBundle);

        return mPaymentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            mContact = (Contact) mBundle.getSerializable("contact");
        }
        rootView = view;
        init();
    }

    private void init() {
        Currency mCurrency = Currency.getInstance(Locale.getDefault());
        currencySimbol = mCurrency.getSymbol();


        ((ImageView) rootView.findViewById(R.id.iv_profile_image)).setImageDrawable(ProfilePicHolder.getInstance().getPic(mContact.getPhoto()));
        ((TextView) rootView.findViewById(R.id.tv_name)).setText(mContact.getName());
        ((TextView) rootView.findViewById(R.id.tv_id)).setText(String.format(Locale.getDefault(), getActivity().getString(R.string.id), mContact.getId()));
        ((TextView) rootView.findViewById(R.id.tv_user_name)).setText(mContact.getUserName());

        ((TextView) rootView.findViewById(R.id.edt_currency_type)).setText(currencySimbol);
        container = rootView.findViewById(R.id.rl_container);

        edtAmount = rootView.findViewById(R.id.edt_amount);
        edtAmount.addTextChangedListener(currencyFormatterTYextWatcher);
        payButton = rootView.findViewById(R.id.btn_pay);
        //  payButton.setVisibility(View.GONE);


        /*Runnable que verificara qual o proximo Runnable a ser executado*/
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                mCards = Database.getInstance().getCards();
                handleCreditCardViews(mCards.size() > 0);
            }
        };

        /*Faz a magica aontecer!*/
        new Thread(mRunnable).start();

    }

    /**
     * Infla a view de cartoes de credito no layout principal de acordo copm a condição recebida
     *
     * @param hasCards variavel de controle que indica qual view devera ser inflada
     */
    private void handleCreditCardViews(boolean hasCards) {

        /*Runnable que exibira na tela uma view para CADASTRAR o primeiro cartao*/
        final Runnable uiRunnableEmpty = new Runnable() {
            @Override
            public void run() {
                View.OnClickListener mClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(mActivity, AddCreditCard.class), ADD_CARD_REQ_CODE);
                    }
                };
                container.removeAllViews();
                /*infla a view no RelativeLayout container e ja seta um listener de cliques*/
                mActivity.getLayoutInflater().inflate(R.layout.view_no_credit_card, container).setOnClickListener(mClickListener);

            }
        };

        /*======================================================== SEPARADOR ========================================================*/


        /*Runnable que exibira na tela uma view para ESCOLHER o  cartao a ser usado na transação*/
        final Runnable uiRunnable = new Runnable() {
            @Override
            public void run() {

                View.OnClickListener mClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectCreditCard();
                    }
                };
                container.removeAllViews();
                View gotCard = mActivity.getLayoutInflater().inflate(R.layout.view_got_credit_card, container);
                gotCard.setOnClickListener(mClickListener);
                tvCreditCardInfo = gotCard.findViewById(R.id.tv_credit_card_info);
                /*Uma vez que o usuario já possui pelo menos um cartão, já seto o primeiro da lista como forma de pagamento*/
                setChoosedCreditCard(mCards.get(0));
            }
        };

        /*======================================================== SEPARADOR ========================================================*/

        /*Este metodo pode ser chamado de uma Thread secundaria. Eu poderia chamar sempre pela UIThread mas assim  teria
         * que criar um metodo para executar o código de cada Runnable separadamente, então, optei por esa abbordagem.*/
        mActivity.runOnUiThread(hasCards ? uiRunnable : uiRunnableEmpty);

    }

    private void selectCreditCard() {
        DialogSelectCreditCard.DialogCallback callback = new DialogSelectCreditCard.DialogCallback() {
            @Override
            public void cardSelected(CreditCard mCreditCard) {
                setChoosedCreditCard(mCreditCard);
            }

            @Override
            public void addNewCard() {
                startActivityForResult(new Intent(mActivity, AddCreditCard.class), ADD_CARD_REQ_CODE_FROM_DIALOG);
            }
        };

        mDialogSelectCreditCard = new DialogSelectCreditCard(mActivity, mCards, callback);
        mDialogSelectCreditCard.show();

    }

    public void setChoosedCreditCard(CreditCard mCreditCard) {
        choosedCreditCard = mCreditCard;
        if (tvCreditCardInfo != null) {
            String brand = mCreditCard.getBrand();
            String nbrs = String.valueOf(mCreditCard.getNumber());
            int lastDigits = Integer.parseInt(nbrs.substring(nbrs.length() - 4, nbrs.length()));
            String strLastDigits = String.valueOf(lastDigits);

            //estilizando texto
            String text = String.format(Locale.getDefault(), getString(R.string.forma_de_pagamento_), brand, lastDigits);

            SpannableString spString = new SpannableString(text);

            int start1 = text.split(brand)[0].length();
            int end1 = text.split(brand)[0].length() + brand.length();
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int start2 = text.split(strLastDigits)[0].length();
            int end2 = text.split(strLastDigits)[0].length() + strLastDigits.length();
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            int start3 = text.split(strLastDigits)[0].length() + strLastDigits.length() + 2;
            int end3 = text.length();

            spString.setSpan(new UnderlineSpan(), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // spString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mActivity, R.color.gray)), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvCreditCardInfo.setText(spString);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CARD_REQ_CODE && resultCode == Activity.RESULT_OK) {
            mCards = Database.getInstance().getCards();
            handleCreditCardViews(mCards.size() > 0);
        } else if (requestCode == ADD_CARD_REQ_CODE_FROM_DIALOG && resultCode == Activity.RESULT_OK) {
            mCards = Database.getInstance().getCards();
            handleCreditCardViews(mCards.size() > 0);
            if (mDialogSelectCreditCard != null && mDialogSelectCreditCard.isShowing())
                mDialogSelectCreditCard.update();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private TextWatcher currencyFormatterTYextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * @param s      s
         * @param start  start
         * @param before before
         * @param count  count
         *               Faz a formatação do valor em tempo real
         */

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtAmount.removeTextChangedListener(this);
            String newChar = String.valueOf(s.length() > 0 ? s.charAt(s.length() - 1) : "");

            if (before == 0)
                amount = amount.concat(newChar);
            else if (amount.length() > 0)
                amount = amount.substring(0, amount.length() - 1);/*usuario apagou um caracter*/

            String amountDecimal = AppPatterns.toDecimal(amount).divide(new BigDecimal(100), 2, RoundingMode.HALF_DOWN).toString();
            String formattedAmount = AppPatterns.convertCurrency(amountDecimal).replace(currencySimbol, "");

            edtAmount.setText(formattedAmount);
            edtAmount.setSelection(edtAmount.getText().length());
            edtAmount.addTextChangedListener(this);
            if (amount.length() > 2) payButton.setVisibility(View.VISIBLE);


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

}
