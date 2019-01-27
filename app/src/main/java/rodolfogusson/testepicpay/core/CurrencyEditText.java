package rodolfogusson.testepicpay.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import rodolfogusson.testepicpay.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by bulent.turkmen on 8/9/2016.
 */
public class CurrencyEditText extends androidx.appcompat.widget.AppCompatEditText {
    private char mGroupDivider;
    private char mMonetaryDivider;
    private String mLocale = "";
    private boolean mShowSymbol;

    private char groupDivider;
    private char monetaryDivider;

    private Locale locale;
    private DecimalFormat numberFormat;

    private int fractionDigit;
    private String currencySymbol;

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.currencyEditText, 0, 0);

        try {
            if (a.getString(R.styleable.currencyEditText_groupDivider) != null) {
                this.mGroupDivider = a.getString(R.styleable.currencyEditText_groupDivider).charAt(0);
                this.groupDivider = mGroupDivider;
            }

            if (a.getString(R.styleable.currencyEditText_monetaryDivider) != null) {
                this.mMonetaryDivider = a.getString(R.styleable.currencyEditText_monetaryDivider).charAt(0);
                this.monetaryDivider = mMonetaryDivider;
            }

            if (a.getString(R.styleable.currencyEditText_locale) == null)
                this.locale = getDefaultLocale();
            else this.mLocale = a.getString(R.styleable.currencyEditText_locale);

            if (a.getString(R.styleable.currencyEditText_showSymbol) != null)
                this.mShowSymbol = a.getBoolean(R.styleable.currencyEditText_showSymbol, false);

            if (mLocale.equals("")) {
                locale = getDefaultLocale();
            } else {
                if (mLocale.contains("-"))
                    mLocale = mLocale.replace("-", "_");

                String[] l = mLocale.split("_");
                if (l.length > 1) {
                    locale = new Locale(l[0], l[1]);
                } else {
                    locale = new Locale("", mLocale);
                }
            }

            initSettings();
        } finally {
            a.recycle();
        }

        this.addTextChangedListener(onTextChangeListener);
    }

    /***
     * If user does not provide a valid locale it throws IllegalArgumentException.
     *
     * If throws an IllegalArgumentException the locale sets to default locale
     */
    private void initSettings() {
        boolean success = false;
        while (!success) {
            try {
                fractionDigit = Currency.getInstance(locale).getDefaultFractionDigits();

                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
                if (mGroupDivider > 0)
                    symbols.setGroupingSeparator(mGroupDivider);
                groupDivider = symbols.getGroupingSeparator();

                if (mMonetaryDivider > 0)
                    symbols.setMonetaryDecimalSeparator(mMonetaryDivider);
                    symbols.setDecimalSeparator(mMonetaryDivider);
                monetaryDivider = symbols.getMonetaryDecimalSeparator();

                currencySymbol = symbols.getCurrencySymbol();

                //DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
                //DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
                numberFormat = new DecimalFormat("#,###,##0.00", symbols);

                success = true;
            } catch (IllegalArgumentException e) {
                Log.e(getClass().getCanonicalName(), e.getMessage());
                locale = getDefaultLocale();
            }
        }
    }

    private Locale getDefaultLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getContext().getResources().getConfiguration().getLocales().get(0);
        else
            return getContext().getResources().getConfiguration().locale;
    }

    /***
     *It resets text currently displayed If user changes separators or locale etc.
     */
    private void resetText() {
        String s = getText().toString();
        if (s.isEmpty()) {
            initSettings();
            return;
        }

        s = s.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                .replace(".", "").replace(" ", "")
                .replace(currencySymbol, "").trim();
        try {
            initSettings();
            s = format(s);
            removeTextChangedListener(onTextChangeListener);
            setText(s);
            setSelection(s.length());
            addTextChangedListener(onTextChangeListener);
        } catch (ParseException e) {
            Log.e(getClass().getCanonicalName(), e.getMessage());
        }
    }

    private abstract class CustomTextWatcher implements TextWatcher {
        EditText editText;

        CustomTextWatcher(EditText editText) {
            this.editText = editText;
        }
    }

    private TextWatcher onTextChangeListener = new CustomTextWatcher(this) {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
                return;

            removeTextChangedListener(this);

            /***
             * Clear input to get clean text before format
             * '\u0020' is empty character
             */
            String text = s.toString();
            text = text.replaceAll ("\\s+", "");
            text = text.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                    .replace(".", "").replace(" ", "")
                    .replace(currencySymbol, "").trim();

            try {
                text = format(text);
            } catch (ParseException e) {
                Log.e(getClass().getCanonicalName(), e.getMessage());
            }

            setText(text);
            setSelection(editText.getText().toString().trim().length());

            addTextChangedListener(this);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private String format(String text) throws ParseException {
        if (mShowSymbol)
            return numberFormat.format(Double.parseDouble(text) / Math.pow(10, fractionDigit));
        else
            return numberFormat.format(Double.parseDouble(text) / Math.pow(10, fractionDigit)).replace(currencySymbol, "");
    }

    /***
     * returns the decimal separator for current locale
     * for example; input value 1,234.56
     *              returns ','
     *
     * @return decimal separator char
     */
    public char getGroupDivider() {
        return groupDivider;
    }

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     *              for input value 1,234.56
     *              set ','
     * @param groupDivider char
     */
    public void setGroupDivider(char groupDivider) {
        this.mGroupDivider = groupDivider;
        resetText();
    }

    /***
     * returns the monetary separator for current locale
     * for example; input value 1,234.56
     *              returns '.'
     *
     * @return monetary separator char
     */
    public char getMonetaryDivider() {
        return monetaryDivider;
    }

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     *              for input value 1,234.56
     *              set '.'
     * @param monetaryDivider char
     */
    public void setMonetaryDivider(char monetaryDivider) {
        this.mMonetaryDivider = monetaryDivider;
        resetText();
    }

    /***
     *
     * @return current locale
     */
    public Locale getLocale() {
        return locale;
    }

    /***
     * Sets locale which desired currency format
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        resetText();
    }

    /**
     *
     * @return true if currency symbol of current locale is showing
     */
    public boolean showSymbol() {
        return this.mShowSymbol;
    }

    /***
     * Sets if currency symbol of current locale shows
     *
     * @param showSymbol
     */
    public void showSymbol(boolean showSymbol) {
        this.mShowSymbol = showSymbol;
        resetText();
    }

    /**
     *
     *  @return double value for current text
     */
    public double getCurrencyDouble() throws ParseException {
        String text = getText().toString();
        text = text.replaceAll("\\s+", "");
        text = text.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                .replace(".", "").replace(" ", "")
                .replace(currencySymbol, "").trim();

        if (showSymbol())
            return Double.parseDouble(text.replace(currencySymbol, "")) / Math.pow(10, fractionDigit);
        else return Double.parseDouble(text) / Math.pow(10, fractionDigit);
    }

    /**
     *
     *  @return String value for current text
     */
    public String getCurrencyText() throws ParseException {
        if (showSymbol())
            return getText().toString().replace(currencySymbol, "");
        else return getText().toString();
    }
}

