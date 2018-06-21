package com.example.eduardo.demoapppagamento.new_card;

// Creditos: https://receitasdecodigo.com.br/android/como-inserir-mascara-em-um-edittext-no-android

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class CardInputMask {

    /**
     * Método que deve ser chamado para realizar a formatação
     *
     * @param ediTxt
     * @param mask
     * @return
     */
    public static TextWatcher mask(final EditText ediTxt, final String mask) {

        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                                          final int after) {}

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before,
                                      final int count) {

                if (mask == null)
                    return;

                final String str = CardInputMask.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (final char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (final Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }


            @Override
            public void afterTextChanged(final Editable s) {
            }
        };
    }

    public static String unmask(final String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").replaceAll("[)]", "");
    }
}