package br.com.dalcim.picpay.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * @author Wiliam
 * @since 02/09/2017
 */

public class NumberCreditCardTextWatcher implements TextWatcher {

    private static final char space = ' ';

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0 && (s.length() % 5) == 0) {
            final char c = s.charAt(s.length() - 1);
            if (space == c) {
                s.delete(s.length() - 1, s.length());
            }
        }

        if (s.length() > 0 && (s.length() % 5) == 0) {
            char c = s.charAt(s.length() - 1);
            if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }

    }
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}
