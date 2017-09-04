package br.com.dalcim.picpay.creditcard;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.dalcim.picpay.BaseActivity;
import br.com.dalcim.picpay.R;
import br.com.dalcim.picpay.data.CreditCard;
import br.com.dalcim.picpay.data.local.RepositoryLocaImpl;
import br.com.dalcim.picpay.utils.NumberCreditCardTextWatcher;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditCardActivity extends BaseActivity implements CreditCardContract.View{
    public static final int REQUEST_CODE = 123;

    @BindView(R.id.acc_edt_number)
    EditText edtNumber;

    @BindView(R.id.acc_spn_month_expiry)
    Spinner spnMonthExpiry;

    @BindView(R.id.acc_spn_year_expiry)
    Spinner spnYearExpiry;

    @BindView(R.id.acc_edt_cvv)
    EditText edtCvv;

    private CreditCardContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        ButterKnife.bind(this);

        edtNumber.addTextChangedListener(new NumberCreditCardTextWatcher());
        presenter = new CreditCardPresenter(this, new RepositoryLocaImpl(this));
    }

    @OnClick(R.id.acc_btn_save)
    public void saveOnClick(){
        String number = edtNumber.getText().toString().replace(" ", "");
        String expiryDate = getExpiryDate();
        String cvv = edtCvv.getText().toString();
        presenter.save(number, expiryDate, cvv);
    }

    @Override
    public void onSucessSave(CreditCard creditCard) {
        Toast.makeText(this, "Cartão Salvo com sucesso", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showError(String error) {
        showConfirmDialog(null, error);
    }

    private String getExpiryDate(){
        String month = String.format("%02d", (spnMonthExpiry.getSelectedItemPosition() + 1));
        String year = spnYearExpiry.getSelectedItem().toString();

        return month + "/" + year;
    }
}
