package br.com.picpay.core.util;

import android.text.TextUtils;

public class StringUtil {
    public static String fixString(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        return string.trim();
    }
}
