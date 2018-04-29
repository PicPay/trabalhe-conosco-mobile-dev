package gilianmarques.dev.picpay_test.activities;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import gilianmarques.dev.picpay_test.R;
import gilianmarques.dev.picpay_test.adapters.SpinnerFlagsAdapter;
import gilianmarques.dev.picpay_test.crud.Database;
import gilianmarques.dev.picpay_test.models.CreditCard;
import gilianmarques.dev.picpay_test.utils.AppPatterns;

public class AddCreditCard extends MyActivity {
    private TextInputEditText edtOwnername, edtNumber, edtCvv, edtExpiryDate;
    private Spinner spinnerBrands;
    private final ArrayList<String> mBrands = new ArrayList<>();
    private List<String> originalBrandsList;

    private TranslateAnimation mErrorAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        init();
    }

    private void init() {

        originalBrandsList = Arrays.asList(getResources().getStringArray(R.array.accepted_brands));
        mBrands.add(getString(R.string.selecione_a_bandeira));
        mBrands.addAll(originalBrandsList);

        edtCvv = findViewById(R.id.edt_cvv);
        edtExpiryDate = findViewById(R.id.edt_expiry_date);
        edtNumber = findViewById(R.id.edt_number);
        edtOwnername = findViewById(R.id.edt_owner_name);
        Button btnDone = findViewById(R.id.btn_done);
        spinnerBrands = findViewById(R.id.spinner_flags);

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

        spinnerBrands.setAdapter(new SpinnerFlagsAdapter(mBrands));
    }

    /**
     * @param number o numero do cartão
     *               Usa regex para achar a bandeira do cartão automaticamente.
     *               OBS: Pra funcionar eu preciso saber em qual posição está a string
     *               com o nome correto do cartão em arrays.xml
     *               <p>
     *               Regex pattern explanation: https://stackoverflow.com/a/42758017/7953908
     */
    private void setBrand(long number) {
        String strNumber = String.valueOf(number);

        if (strNumber.matches("^3[47][0-9]{13}$")/*Amex*/) {
            /*busco no array de bandeiras original a string com o nome do cartão (Amex nesse caso) encontro a posição dele no mBrands e seto no spinnerBrands*/
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(0)));
        } else if (strNumber.matches("^(6541|6556)[0-9]{12}$")/*BCG*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(1)));
        } else if (strNumber.matches("^3(?:0[0-5]|[68][0-9])[0-9]{11}$")/*Diners*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(2)));
        } else if (strNumber.matches("^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$")/*Discover*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(3)));
        } else if (strNumber.matches("^(?:2131|1800|35\\d{3})\\d{11}$")/*JCB*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(4)));
        } else if (strNumber.matches("^(6304|6706|6709|6771)[0-9]{12,15}$")/*Laser*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(5)));
        } else if (strNumber.matches("^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$")/*Maestro*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(6)));
        } else if (strNumber.matches("^5[1-5][0-9]{14}$")/*Mastercard*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(7)));
        } else if (strNumber.matches("^(6334|6767)[0-9]{12}|(6334|6767)[0-9]{14}|(6334|6767)[0-9]{15}$")/*Solo*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(8)));
        } else if (strNumber.matches("^(4903|4905|4911|4936|6333|6759)[0-9]{12}|(4903|4905|4911|4936|6333|6759)[0-9]{14}|(4903|4905|4911|4936|6333|6759)[0-9]{15}|564182[0-9]{10}|564182[0-9]{12}|564182[0-9]{13}|633110[0-9]{10}|633110[0-9]{12}|633110[0-9]{13}$")/*Switch*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(9)));
        } else if (strNumber.matches("^(62[0-9]{14,17})$")/*Uniom pay*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(10)));
        } else if (strNumber.matches("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$")/*Visa*/) {
            spinnerBrands.setSelection(mBrands.indexOf(originalBrandsList.get(11)));
        } else spinnerBrands.setSelection(0);


    }

    private void verifyDataAndClose() {
        if (edtOwnername.getText().toString().isEmpty()) {
            notifyUser(edtOwnername);
            return;
        }

        String number = edtNumber.getText().toString().replace(" ", "");
        if (number.length() != 16) {
            notifyUser(edtNumber);
            return;
        }


        if (edtExpiryDate.getText().length() != 7) {
            notifyUser(edtExpiryDate);
            return;
        }

        if (edtCvv.getText().length() != 3) {
            notifyUser(edtCvv);
            return;
        }

        if (spinnerBrands.getSelectedItemPosition() == 0) {
            notifyUser(spinnerBrands);
            return;
        }


        CreditCard mCreditCard = new CreditCard();
        mCreditCard.setOwnerName(edtOwnername.getText().toString().toUpperCase());
        mCreditCard.setExpireDate(edtExpiryDate.getText().toString());
        mCreditCard.setCvv(Integer.parseInt(edtCvv.getText().toString()));
        mCreditCard.setNumber(Long.parseLong(edtNumber.getText().toString().replace(" ", "")));


        /*para que a bandeira certa seja definida é preciso que sua posição no Spinner coincida com a ID na classe CreditCard
        * EX: Amex é a primeira bandeira da lista e sua constante na clase CreditCard tem valor 0*/
        /*o -1 se da por conta da String que adiciono na primeira posiçao do spinner*/
        mCreditCard.setBrandId(spinnerBrands.getSelectedItemPosition() - 1);

        boolean success = Database.getInstance().insertCard(mCreditCard);

        if (success) {
            AppPatterns.vibrate(AppPatterns.SUCCESS);
            setResult(RESULT_OK);
            finish();
        } else
            AppPatterns.notifyUser(AddCreditCard.this, getString(R.string.erro_ao_cadastrar_cartao), AppPatterns.ERROR);

    }

    /*===================================================== TEXTWATCHERS BEGIN ===================================================== */

        /*Esses TextWatchers são responsaveis por formatar os campos de texto*/

    private final TextWatcher mNumberTextWatcher = new TextWatcher() {
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
                setBrand(Long.parseLong(s.toString().replace(" ", "")));
                edtExpiryDate.requestFocus();
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher mExpiryTextWatcher = new TextWatcher() {
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

    private final TextWatcher mCvvTextWatcher = new TextWatcher() {
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
