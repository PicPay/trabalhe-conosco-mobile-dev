package br.com.picpay.picpay.custom;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

import br.com.picpay.picpay.textWatcher.ValueTextWatcher;

public class ValueTextEditText extends CustomEditText {

    public ValueTextEditText(Context context) {
        super(context);
        init();
    }

    public ValueTextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ValueTextEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setInputType(InputType.TYPE_CLASS_NUMBER);
        addTextChangedListener(new ValueTextWatcher(this, Integer.MAX_VALUE));
    }

    public float getValue() {
        try {
            String valueString = getText().toString().replaceAll("[R$,.]", "");
            return Float.parseFloat(valueString) / 100;
        } catch (Exception e) {
            return 0;
        }
    }
}
