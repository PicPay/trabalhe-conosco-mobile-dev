package com.example.eduardo.demoapppagamento.payment;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class CurrencyMask implements TextWatcher {

    boolean ignoreChange = false;
    final EditText editText;

    public CurrencyMask(EditText editText) {
        super();
        this.editText = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {
        if (!ignoreChange) {
            /*String string = s.toString();
            double parsed = Double.parseDouble(string);

            DecimalFormat df = new DecimalFormat("#.00");
            string = df.format(parsed);

            string = NumberFormat.getCurrencyInstance().format((parsed/100));*/

            String string = s.toString();
            string = string.replace(".", "");
            string = string.replace(" ", "");
            switch (string.length()) {
                case 0: string = ".  "; break;
                case 1: string = ". " + string; break;
                case 2: string = "." + string; break;
                default:
                    string = string.substring(0, string.length() - 2) + "." + string.substring(string.length() - 2, string.length());
            }
            ignoreChange = true;
            editText.setText(string);
            editText.setSelection(editText.getText().length());
            ignoreChange = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}