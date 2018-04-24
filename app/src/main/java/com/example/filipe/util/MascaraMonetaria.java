package com.example.filipe.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by filipe on 23/04/18.
 */

public class MascaraMonetaria implements TextWatcher {

    private EditText campo;
    private boolean isUpdating = false;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public MascaraMonetaria(EditText campo){
        super();
        this.campo = campo;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Evita que o método seja executado varias vezes.
        // Se tirar ele entre em loop
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = charSequence.toString();

        // Verifica se já existe a máscara no texto.
        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                (str.indexOf(".") > -1 || str.indexOf(",") > -1));

        // Verificamos se existe máscara
        if (hasMask) {
            // Retiramos a máscara.
            str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                    .replaceAll("[.]", "");
        }

        try {
            // Transformamos o número que está escrito no EditText em
            // monetário.
            str = nf.format(Double.parseDouble(str) / 100);
            campo.setText(str);
            campo.setSelection(campo.getText().length());
        } catch (NumberFormatException e) {
            charSequence = "";
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
