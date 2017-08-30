package com.ghrc.picpay.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyTextWatcher implements TextWatcher {
    private String current = "";
    private int index;
    private boolean deletingDecimalPoint;
    private final EditText currency;
    public CurrencyTextWatcher(EditText p_currency) {
        currency = p_currency;
    }
    @Override
    public void onTextChanged(
            CharSequence p_s, int p_start, int p_before, int p_count
    ) {
        // nothing to do
    }
    @Override
    public void beforeTextChanged(
            CharSequence p_s, int p_start, int p_count, int p_after
    ) {
        if (p_after>0) {
            index = p_s.length() - p_start;
        } else {
            index = p_s.length() - p_start - 1;
        }
        if (p_count>0 && p_s.charAt(p_start)=='.') {
            deletingDecimalPoint = true;
        } else {
            deletingDecimalPoint = false;
        }
    }
    @Override
    public synchronized void afterTextChanged(Editable p_s) {
        if(!p_s.toString().equals(current)){
            currency.removeTextChangedListener(this);
            if (deletingDecimalPoint) {
                p_s.delete(p_s.length()-index-1, p_s.length()-index);
            }
            // Currency char may be retrieved from NumberFormat.getCurrencyInstance()
            String v_text;
            if(Locale.getDefault().getCountry().equals("BR")){
                v_text = p_s.toString().replaceAll("[R$,.]", "");}
            else {
                v_text = p_s.toString().replaceAll("[$,.]", "");
            }
            double v_value = 0;
            if (v_text!=null && v_text.length()>0) {
                v_value = Double.parseDouble(v_text);
            }
            // Currency instance may be retrieved from a static member.
            //NumberFormat.getInstance(Locale.US)
            String v_formattedValue = NumberFormat.getCurrencyInstance().format((v_value / 100));
            current = v_formattedValue;
            currency.setText(v_formattedValue);
            if (index>v_formattedValue.length()) {
                currency.setSelection(v_formattedValue.length());
            } else {
                currency.setSelection(v_formattedValue.length()-index);
            }
            // inlude here anything you may want to do after the formatting is completed.
            currency.addTextChangedListener(this);
        }
    }
}