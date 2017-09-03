package br.com.dalcim.picpay.creditcard;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.utils.NumberCreditCardTextWatcher;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreditCardActivity extends BaseActivity implements CreditCardContract.View{

    @BindView(R.id.acc_edt_number)
    EditText edtNumber;

    @BindView(R.id.acc_spn_month_expiry)
    Spinner spnMonthExpiry;

    @BindView(R.id.acc_spn_year_expiry)
    Spinner spnYearExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        ButterKnife.bind(this);

        edtNumber.addTextChangedListener(new NumberCreditCardTextWatcher());
    }


}
