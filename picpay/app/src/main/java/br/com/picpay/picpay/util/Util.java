package br.com.picpay.picpay.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean(scope = EBean.Scope.Singleton)
public class Util {

    @RootContext
    Context context;

    public Float convertDpToPixel(float dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float densidade = displayMetrics.density;
        return (dp * densidade);
    }

    public void setCursorEndEdittext(EditText... listEditText) {
        for (EditText editText : listEditText) {
            if (editText.getText() != null && editText.getText().length() > 0) {
                editText.setSelection(editText.getText().length());
            }
        }
    }

    public static void closeKeyboardEditText(Context context, EditText... editText) {
        try {
            for (EditText e : editText) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
