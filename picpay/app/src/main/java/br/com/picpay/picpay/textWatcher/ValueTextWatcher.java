package br.com.picpay.picpay.textWatcher;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

public class ValueTextWatcher implements TextWatcher {

    private EditText editText;
    private String current = "";
    private int maxValue;

    public ValueTextWatcher(@NonNull EditText editText, int maxValue) {
        this.editText = editText;
        this.maxValue = maxValue;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[R$,.]", "");
            String cleanStringParsed = current.replaceAll("[R$,.]", "");

            double parsed = Double.parseDouble(cleanString);
            String formatted = "";
            if (parsed / 100 > maxValue) {
                parsed = Double.parseDouble(cleanStringParsed);
            }

            if (parsed > 0) {
                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));
            }

            current = formatted;
            editText.setText(formatted);
            editText.setSelection(formatted.length());

            editText.addTextChangedListener(this);
        }
    }
}