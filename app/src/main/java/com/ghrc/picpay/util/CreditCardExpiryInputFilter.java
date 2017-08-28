package com.ghrc.picpay.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Guilherme on 27/08/2017.
 */

public class CreditCardExpiryInputFilter implements InputFilter {

    private final String currentYearLastTwoDigits;
    public CreditCardExpiryInputFilter() {
        currentYearLastTwoDigits = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //do not insert if length is already 5
        if (dest != null & dest.toString().length() == 5) return "";
        //do not insert more than 1 character at a time
        if (source.length() > 1) return "";
        //only allow character to be inserted at the end of the current text
        if (dest.length() > 0 && dstart != dest.length()) return "";

        //if backspace, skip
        if (source.length() == 0) {
            return source;
        }

        //At this point, `source` is a single character being inserted at `dstart`.
        //`dstart` is at the end of the current text.

        final char inputChar = source.charAt(0);

        if (dstart == 0) {
            //first month digit
            if (inputChar > '1') return "";
        }
        if (dstart == 1) {
            //second month digit
            final char firstMonthChar = dest.charAt(0);
            if (firstMonthChar == '0' && inputChar == '0') return "";
            if (firstMonthChar == '1' && inputChar > '2') return "";

        }
        if (dstart == 2) {
            final char currYearFirstChar = currentYearLastTwoDigits.charAt(0);
            if (inputChar < currYearFirstChar) return "";
            return "/".concat(source.toString());
        }
        if (dstart == 4){
            final String inputYear = ""+dest.charAt(dest.length()-1)+source.toString();
            if (inputYear.compareTo(currentYearLastTwoDigits) < 0) return "";
        }

        return source;
    }
}
