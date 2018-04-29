package gilianmarques.dev.picpay_test.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
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
import gilianmarques.dev.picpay_test.asyncs.ProfilePictureUtils;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.DialogSelectCreditCard;
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
    private String amount = "";
    private RelativeLayout container;
    private Activity mActivity;
    private ArrayList<CreditCard> mCards = new ArrayList<>();
    private TextView tvCreditCardInfo;
    private CreditCard choosedCreditCard;
    private DialogSelectCreditCard mDialogSelectCreditCard;


    public static PaymentFragment newInstance(TransactionCallback callback, Contact mContact) {
        PaymentFragment mPaymentFragment = new PaymentFragment();
        mPaymentFragment.callback = callback;
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("contact", mContact);
        mPaymentFragment.setArguments(mBundle);

        return mPaymentFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle mBundle = getArguments();

        mActivity = getActivity();
        if (mActivity != null) {
            ActionBar mActionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
            if (mActionBar != null) mActionBar.setTitle(getString(R.string.Insira_os_dados));
        }

        if (mBundle != null) {
            mContact = (Contact) mBundle.getSerializable("contact");
        }
        rootView = view;
        init();
    }


    private void init() {
        String currencySymbol = Currency.getInstance(Locale.getDefault()).getSymbol();

        ((ImageView) rootView.findViewById(R.id.iv_profile_image)).setImageDrawable(new ProfilePictureUtils(mContact).getProfilePic());
        ((TextView) rootView.findViewById(R.id.tv_name)).setText(mContact.getName());
        ((TextView) rootView.findViewById(R.id.tv_id)).setText(String.format(Locale.getDefault(), mActivity.getString(R.string.id), mContact.getId()));
        ((TextView) rootView.findViewById(R.id.tv_user_name)).setText(mContact.getUserName());

        container = rootView.findViewById(R.id.rl_container);

        edtAmount = rootView.findViewById(R.id.edt_amount);
        edtAmount.setHint(AppPatterns.convertCurrency("0"));
        InputFilter[] mInputFilters = new InputFilter[1];
        mInputFilters[0] = new InputFilter.LengthFilter(String.valueOf(currencySymbol).concat("999.999,99").length());
        edtAmount.setFilters(mInputFilters);


        edtAmount.addTextChangedListener(currencyFormatterTextWatcher);
        Button payButton = rootView.findViewById(R.id.btn_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyTransactionReadyToGo();
            }
        });

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                mCards = Database.getInstance().getCards();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleCreditCardViews(mCards.size() > 0);

                    }
                });
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
    @UiThread
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
                tvCreditCardInfo.setText(R.string.selecione_uma_forma_de_pagamento);
            }
        };


        if (hasCards) uiRunnable.run();
        else uiRunnableEmpty.run();


    }

    /**
     * Exibe um dialogo que permite ao usuario escolher ou adicionar um cartão de crédito
     */
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

        mDialogSelectCreditCard = new DialogSelectCreditCard(mActivity, callback);
        mDialogSelectCreditCard.show();

    }

    /**
     * @param mCreditCard  O cartão que seleciondo pelo usuario
     *
     * Salva na declaração choosedCreditCard a instancia do cartão de crédito  selecionado pelo usuário
     * e altera o {@link TextView} com a informação de pagamento
     */
    private void setChoosedCreditCard(CreditCard mCreditCard) {
        choosedCreditCard = mCreditCard;
        if (tvCreditCardInfo != null) {
            String brand = mCreditCard.getBrandName();
            String nbrs = String.valueOf(mCreditCard.getNumber());
            int lastDigits = Integer.parseInt(nbrs.substring(nbrs.length() - 4, nbrs.length()));
            String strLastDigits = String.valueOf(lastDigits);

            //estilizando texto
            String text = String.format(Locale.getDefault(), getString(R.string.forma_de_pagamento_), brand, lastDigits);

            SpannableString spString = new SpannableString(text);

            /*BANDEIRA*/
            int start1 = text.split(brand)[0].length();
            int end1 = text.split(brand)[0].length() + brand.length();
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            /*4 ultimos digitos*/
            int start2 = text.split(strLastDigits)[0].length();
            int end2 = text.split(strLastDigits)[0].length() + strLastDigits.length();
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            /*Usar outro cartão*/
            int start3 = text.split(strLastDigits)[0].length() + strLastDigits.length() + 2;
            int end3 = text.length();
            spString.setSpan(new UnderlineSpan(), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvCreditCardInfo.setText(spString);
        }

    }

    /**
     * Verifica se os dados do formulário foram corretamente inseridos, caso sim notifica a {@link gilianmarques.dev.picpay_test.activities.SendCashActivity}
     *  de que a transação pode prosseguir, caso não, notifca o usuário de que o campo está preenchido de forma incorreta
     */
    private void notifyTransactionReadyToGo() {
        String strAmount = edtAmount.getText().toString();

        float amount = strAmount.isEmpty() ? 0f : AppPatterns.toDecimal(strAmount).floatValue();

        if (amount < 0.10) {
            AppPatterns.notifyUser(mActivity, String.format(Locale.getDefault(), getString(R.string.o_valor_minimo_para_transacoes), AppPatterns.convertCurrency("0.10")), AppPatterns.ERROR);
            return;
        }

        if (choosedCreditCard == null) {
            AppPatterns.notifyUser(mActivity, getString(R.string.selecione_uma_forma), AppPatterns.ERROR);
            return;
        }

        if (callback != null) callback.cardAndAmountSet(choosedCreditCard, amount);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CARD_REQ_CODE && resultCode == Activity.RESULT_OK) {

            mCards = Database.getInstance().getCards();
            handleCreditCardViews(mCards.size() > 0);
            if (mCards.size() == 1) setChoosedCreditCard(mCards.get(0));

        } else if (requestCode == ADD_CARD_REQ_CODE_FROM_DIALOG && resultCode == Activity.RESULT_OK) {

            mCards = Database.getInstance().getCards();
            handleCreditCardViews(mCards.size() > 0);
            if (mDialogSelectCreditCard != null && mDialogSelectCreditCard.isShowing())
                mDialogSelectCreditCard.update(mCards.get(mCards.size() - 1));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private final TextWatcher currencyFormatterTextWatcher = new TextWatcher() {
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
            String formattedAmount = AppPatterns.convertCurrency(amountDecimal);

            edtAmount.setText(formattedAmount);
            edtAmount.setSelection(edtAmount.getText().length());
            edtAmount.addTextChangedListener(this);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };


}
