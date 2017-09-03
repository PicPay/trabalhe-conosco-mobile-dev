package br.com.dalcim.picpay.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * @author Wiliam
 * @since 02/09/2017
 */

public class DecimalTextWatcher implements TextWatcher {

    private boolean editing = false;
    private final EditText field;
    private NumberFormat format = NumberFormat.getCurrencyInstance();

    public DecimalTextWatcher(EditText field) {
        this.field = field;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (editing) {
            editing = false;
            return;
        }

        editing = true;

        try {
            double value = Double.parseDouble(desmascarar(s.toString()));

            while(value > 100_000_000d){
                value /= 10;
            }

            value /= 100d;

            field.setText(format.format(value));
            field.setSelection(field.getText().length());
        } catch (NumberFormatException ignored) {

        }
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
    @Override public void afterTextChanged(Editable s){}

    private String desmascarar(String str) {
        return str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
    }
}