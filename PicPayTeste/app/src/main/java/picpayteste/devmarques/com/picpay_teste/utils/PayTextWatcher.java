package picpayteste.devmarques.com.picpay_teste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Locale;

public class PayTextWatcher implements TextWatcher
{
    public static final String T = "PayTextWatcher";

    private final EditText editText;
    protected int max_length = Integer.MAX_VALUE;
    private String formatType;
    private String current = "";
    private boolean insertingSelected = false;
    private boolean isDeleting;

    /**
     * @param editText
     * @param formatType String formatting style like "%,.2f $"
     */
    public PayTextWatcher(EditText editText, String formatType)
    {
        this.editText = editText;
        this.formatType = formatType;
        Log.e(T, "::PayTextWatcher:" + "formatType " + formatType);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        Log.i(T, "::beforeTextChanged:" + "CharSequence " + s + " start=" + start + " count=" + count + " after=" +
                after);
        if (after <= 0 && count > 0)
        {
            isDeleting = true;
        } else
        {
            isDeleting = false;
        }
        if (!s.toString().equals(current))
        {
            editText.removeTextChangedListener(this);
            String clean_text = s.toString().replaceAll("[^\\d]", "");
            editText.setText(clean_text);
            editText.addTextChangedListener(this);
        }

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        Log.i(T, "::onTextChanged:" + "CharSequence " + s + " start=" + start + " count=" + count + " before=" +
                before);
        if (start == 0 && before >= 4)
        {
            insertingSelected = true;
        }
    }


    @Override
    public synchronized void afterTextChanged(Editable s)
    {
        Log.i(T, "::afterTextChanged:" + "Editable " + s + "; Current " + current);
        if (!s.toString().equals(current))
        {
            editText.removeTextChangedListener(this);
            String digits = s.toString();

            if (insertingSelected)
            {
                digits = String.valueOf(toDouble(digits));
            }
            String formatted_text;
            double v_value = 0;
            try
            {
                formatted_text = String.format(new Locale("pt", "BR"), formatType, Double.parseDouble(digits));

            } catch (NumberFormatException nfe)
            {
                v_value = toDouble(digits);
                formatted_text = String.format(new Locale("pt", "BR"), formatType, v_value);
            }

            current = formatted_text;
            editText.setText(formatted_text);
            editText.setSelection(formatted_text.length());
            editText.addTextChangedListener(this);
        }

    }

    private String deleteLastChar(String clean_text)
    {
        if (clean_text.length() > 0)
        {
            clean_text = clean_text.substring(0, clean_text.length() - 1);
        } else
        {
            clean_text = "0";
        }
        return clean_text;
    }

    /**
     * @param str String with special caracters
     *
     * @return a double value of string
     */
    public double toDouble(String str)
    {
        str = str.replaceAll("[^\\d]", "");
        if (str != null && str.length() > 0)
        {

            double value = Double.parseDouble(str);
            String s_value = Double.toString(Math.abs(value / 100));
            int integerPlaces = s_value.indexOf('.');
            if (integerPlaces > max_length)
            {
                value = Double.parseDouble(deleteLastChar(str));
            }

            return value / 100;
        } else
        {
            return 0;
        }
    }


    public int getMax_length()
    {
        return max_length;
    }


    public void setMax_length(int max_length)
    {
        this.max_length = max_length;
    }

}