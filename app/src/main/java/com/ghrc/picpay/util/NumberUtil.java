package com.ghrc.picpay.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Guilherme on 28/08/2017.
 */

public class NumberUtil {
    public static double ParseNumber(String valor){
        String myMoney = valor;
        String s;
        try {
            s = DecimalFormat.getCurrencyInstance(Locale.getDefault()).parse (myMoney).toString();
        } catch (ParseException e) {
            return Double.parseDouble("0");
        }
        return Double.parseDouble(s);
    }
}
