package mobiledev.erickgomes.picpayapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by erickgomes on 24/03/2018.
 */

public class MaskEditText implements TextWatcher {
    private String mMask;
    private EditText mEditText;
    private Set<String> symbolMask = new HashSet<String>();
    private boolean isUpdating;
    private String old = "";

    public MaskEditText(String mask, EditText editText) {
        mMask = mask;
        mEditText = editText;
        initSymbolMask();
    }

    private void initSymbolMask() {
        for (int i = 0; i < mMask.length(); i++) {
            char ch = mMask.charAt(i);
            if (ch != '#')
                symbolMask.add(String.valueOf(ch));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = MaskUtils.unmask(s.toString(), symbolMask);
        String mascara = "";

        if (isUpdating) {
            old = str;
            isUpdating = false;
            return;
        }

        if (str.length() > old.length())
            mascara = MaskUtils.mask(mMask, str);
        else
            mascara = s.toString();

        isUpdating = true;

        mEditText.setText(mascara);
        mEditText.setSelection(mascara.length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}