package gilianmarques.dev.picpay_test.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.adapters.SpinnerFlagsAdapter;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.utils.AppPatterns;
import us.fatehi.creditcardnumber.AccountNumber;
import us.fatehi.creditcardnumber.BankCard;

public class AddCreditCard extends AppCompatActivity {
    private TextInputEditText edtOwnername, edtNumber, edtCvv, edtExpiryDate;
    private Spinner spinnerFlags;
    private ArrayList<String> mflags = new ArrayList<>();
    private TranslateAnimation mErrorAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        init();
    }

    private void init() {
        mflags.add(getString(R.string.selecione_a_bandeira));
        mflags.add("Mastercard");
        mflags.add("Visa");
        mflags.add("Diners");
        mflags.add("Desconhecido");
        edtCvv = findViewById(R.id.edt_cvv);
        edtExpiryDate = findViewById(R.id.edt_expiry_date);
        edtNumber = findViewById(R.id.edt_number);
        edtOwnername = findViewById(R.id.edt_owner_name);
        Button btnDone = findViewById(R.id.btn_done);
        spinnerFlags = findViewById(R.id.spinner_flags);

        edtNumber.addTextChangedListener(mNumberTextWatcher);
        edtExpiryDate.addTextChangedListener(mExpiryTextWatcher);
        edtCvv.addTextChangedListener(mCvvTextWatcher);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyDataAndClose();
            }
        });

        mErrorAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -0.02f,
                Animation.RELATIVE_TO_SELF, 0.02f, 0, 0.0f, 0, 0.0f);
        mErrorAnimation.setDuration(100);
        mErrorAnimation.setRepeatCount(5);
        mErrorAnimation.setRepeatMode(Animation.REVERSE);
        mErrorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setElevation(15f);


        }
        setSpinnerAdapter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void notifyUser(View mView) {
        mView.startAnimation(mErrorAnimation);
        mView.requestFocus();
        AppPatterns.vibrate(AppPatterns.ERROR);
    }

    private void setSpinnerAdapter() {

        spinnerFlags.setAdapter(new SpinnerFlagsAdapter(mflags));
    }

    /**
     * @param number o numero do cartão
     *               <p>
     *               Usa uma biblioteca de terceiros para sugerir a bandeira do cartão. Outra abordagem seria usar regex mas o numero de padrões para as
     *               bandeiras que encontrei na  internet foi pequeno e adquirir mais consumiria muito do meu tempo em pesquisas.
     *               <p>
     *               Padrões de algumas bandeiras:
     *               <p>
     *               Visa: ^4[0-9]{12}(?:[0-9]{3})
     *               Mastercard: ^5[1-5][0-9]{14}
     *               Amex: ^3[47][0-9]{13}
     *               Diners Club: ^3(?:0[0-5]|[68][0-9])[0-9]{11}
     *               Discover: ^6(?:011|5[0-9]{2})[0-9]{12}
     *               JCB: ^(?:2131|1800|35\d{3})\d{11}
     */
    private void getBrand(long number) {
        BankCard card = new BankCard(new AccountNumber(String.valueOf(number)));
        String brand = card.getPrimaryAccountNumber().getCardBrand().toString();

        int index = mflags.indexOf(brand);


        if (brand.equalsIgnoreCase("Unknown")) {
            /*Não foi possível identificar a bandeira*/
            spinnerFlags.setSelection(spinnerFlags.getAdapter().getCount() - 1);
        } else if (index < 0 || index > mflags.size()) {
            /*bandeira não existe no array de bandeiras*/
            mflags.add(mflags.size() - 1, brand);
            setSpinnerAdapter();
            getBrand(number);
        } else spinnerFlags.setSelection(index);
    }

    private void verifyDataAndClose() {
        int successCount = 0;
        if (!edtOwnername.getText().toString().isEmpty()) successCount++;
        else {
            notifyUser(edtOwnername);
            return;
        }

        /*Considera-se que um cartão pode ter entre 13 e 16 dígitos (que eu saiba)*/
        String number = edtNumber.getText().toString().replace(" ", "");
        if (number.length() > 12 && number.length() < 17) {
            successCount++;
        } else {
            notifyUser(edtNumber);
            return;
        }


        if (edtExpiryDate.getText().length() == 7) successCount++;
        else {
            notifyUser(edtExpiryDate);
            return;
        }

        if (edtCvv.getText().length() == 3) successCount++;
        else {
            notifyUser(edtCvv);
            return;
        }

        if (spinnerFlags.getSelectedItemPosition() > 0) successCount++;
        else {
            notifyUser(spinnerFlags);
            return;
        }


        if (successCount == 5) {

            CreditCard mCreditCard = new CreditCard();
            mCreditCard.setOwnerName(edtOwnername.getText().toString().toUpperCase());
            mCreditCard.setExpireDate(edtExpiryDate.getText().toString());
            mCreditCard.setCvv(Integer.parseInt(edtCvv.getText().toString()));
            mCreditCard.setNumber(Long.parseLong(edtNumber.getText().toString().replace(" ", "")));
            mCreditCard.setBrand(mflags.get(spinnerFlags.getSelectedItemPosition()));

            boolean success = Database.getInstance().insertCard(mCreditCard);

            if (success) {
                AppPatterns.vibrate(AppPatterns.SUCCESS);
                setResult(RESULT_OK);
                finish();
            } else
                AppPatterns.notifyUser(AddCreditCard.this, getString(R.string.erro_ao_cadastrar_cartao), AppPatterns.ERROR);
        }
    }
    /*===================================================== TEXTWATCHERS BEGIN ===================================================== */

    private TextWatcher mNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (before == 0) if (s.length() == 4 || s.length() == 9 || s.length() == 14) {
                String str = s.toString().concat(" ");
                edtNumber.removeTextChangedListener(this);
                edtNumber.setText(str);
                edtNumber.setSelection(edtNumber.getText().length());
                edtNumber.addTextChangedListener(this);
            }
            if (s.length() == 19) {
                getBrand(Long.parseLong(s.toString().replace(" ", "")));
                edtExpiryDate.requestFocus();
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher mExpiryTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (before == 0) {
                if (s.length() == 2) {
                    String str;

                    // limito o mese a 12
                    int mounth = Integer.parseInt(s.toString());
                    if (mounth > 12 || mounth < 1)
                        str = "12/";
                    else str = s.toString().concat("/");

                    edtExpiryDate.removeTextChangedListener(this);
                    edtExpiryDate.setText(str);
                    edtExpiryDate.setSelection(edtExpiryDate.getText().length());
                    edtExpiryDate.addTextChangedListener(this);

                } else if (s.length() == 7) {
                    String str;

                    // limito o ano  a + 30 anos
                    int additional = 30;
                    int year = Integer.parseInt(s.toString().split("/")[1]);
                    int yearLimit = GregorianCalendar.getInstance().get(Calendar.YEAR) + additional;
                    if (year > yearLimit || year < yearLimit - additional)
                        str = s.toString().split("/")[0].concat("/".concat(String.valueOf(yearLimit)));
                    else str = s.toString().concat("/");

                    edtExpiryDate.removeTextChangedListener(this);
                    edtExpiryDate.setText(str);
                    edtExpiryDate.setSelection(edtExpiryDate.getText().length());
                    edtExpiryDate.addTextChangedListener(this);
                    edtCvv.requestFocus();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher mCvvTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 3) {
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (mInputMethodManager != null)
                mInputMethodManager.hideSoftInputFromWindow(edtCvv.getWindowToken(), 0);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /*===================================================== TEXTWATCHERS END ===================================================== */


}
