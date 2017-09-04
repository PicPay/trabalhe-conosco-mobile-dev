package br.com.dalcim.picpay.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author Wiliam
 * @since 02/09/2017
 */

public class ExpiryDataTextWatcher implements TextWatcher {

    private final EditText field;

    public ExpiryDataTextWatcher(EditText field) {
        this.field = field;
    }


    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (before == 1 && count == 2 && s.charAt(s.length()-1) != '/') {
            field.setText(field.getText().toString() + "/");
        }
        if (field.getText().toString().toCharArray().length < 3) {
            field.setText(field.getText().toString().replace("/", ""));
        }
    }
}