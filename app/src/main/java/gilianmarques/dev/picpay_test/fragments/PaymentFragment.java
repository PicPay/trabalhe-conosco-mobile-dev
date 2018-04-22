package gilianmarques.dev.picpay_test.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
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
import java.util.Currency;
import java.util.Locale;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.models.Contact;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import gilianmarques.dev.picpay_test.utils.TransactionCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements TextWatcher {

    private TransactionCallback callback;
    private Contact mContact;
    private View rootView;
    private EditText edtAmount;
    private String currencySimbol, amount = "";
    private RelativeLayout container;
    private Button payButton;

    /**
     * @param callback c
     * @return r
     * @see ContactsListFragment para uma descrição
     */
    public PaymentFragment attachCallback(TransactionCallback callback) {
        this.callback = callback;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContact = (Contact) getArguments().getSerializable("contact");
        rootView = view;
        init();
    }

    private void init() {
        Currency mCurrency = Currency.getInstance(Locale.getDefault());
        currencySimbol = mCurrency.getSymbol();


        ((ImageView) rootView.findViewById(R.id.iv_profile_image)).setImageDrawable(mContact.getPhotoDrawable());
        ((TextView) rootView.findViewById(R.id.tv_name)).setText(mContact.getName());
        ((TextView) rootView.findViewById(R.id.tv_id)).setText(String.format(Locale.getDefault(), getActivity().getString(R.string.id), mContact.getId()));
        ((TextView) rootView.findViewById(R.id.tv_user_name)).setText(mContact.getUserName());

        ((TextView) rootView.findViewById(R.id.edt_currency_type)).setText(currencySimbol);
        container = rootView.findViewById(R.id.rl_container);

        edtAmount = rootView.findViewById(R.id.edt_amount);
        edtAmount.addTextChangedListener(this);
        payButton = rootView.findViewById(R.id.btn_pay);
      //  payButton.setVisibility(View.GONE);

        View noCreditCard = getActivity().getLayoutInflater().inflate(R.layout.view_no_credit_card, container);

    }


    public PaymentFragment newInstance(Contact mContact) {
        PaymentFragment mPaymentFragment = new PaymentFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("contact", mContact);
        mPaymentFragment.setArguments(mBundle);

        return mPaymentFragment;
    }


    /*============================================ TextWatcher interface BEGIN ============================================*/
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
        if (amount.length()>2) payButton.setVisibility(View.VISIBLE);


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    /*============================================ TextWatcher interface END ============================================*/
}
