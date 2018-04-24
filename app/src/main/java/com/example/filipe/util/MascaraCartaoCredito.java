package com.example.filipe.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by filipe on 23/04/18.
 */

public class MascaraCartaoCredito implements TextWatcher {

    public static final String FORMAT_NUMBER = "#### #### #### ####";
    public static final String FORMAT_DATE = "##/##";

    private EditText campo;
    private String mascara;
    private boolean isUpdating = false;
    private String old = "";

    public MascaraCartaoCredito(EditText campo, String mascara){
        super();
        this.campo = campo;
        this.mascara = mascara;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Evita que o método seja executado varias vezes.
        // Se tirar ele entre em loop
        String str_retorno = "";
        if (isUpdating) {
            old = charSequence.toString().replaceAll("[ ]", "").replaceAll("[/]", "");
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = charSequence.toString();

        // Verifica se já existe a máscara no texto.
        boolean hasMask = str.indexOf(" ") > -1 || str.indexOf("/") > -1;

        // Verificamos se existe máscara
        if (hasMask) {
            // Retiramos a máscara.
            str = str.replaceAll("[ ]", "").replaceAll("[/]", "");
        }

        int index = 0;
        for(final char m : mascara.toCharArray()){
            if(m != '#' && str.length() > old.length()){
                str_retorno += m;
                continue;
            }
            try {
                str_retorno += str.charAt(index);
            } catch (final Exception e){
                break;
            }
            index++;
        }

        isUpdating = true;
        campo.setText(str_retorno);
        campo.setSelection(str_retorno.length());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
