package com.ghrc.picpay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Guilherme on 29/08/2017.
 */

public class DateUtil {
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String dateBR(String date){
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.getDefault()).format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).parse(date));
        } catch (ParseException e) {
            return "Conversão error..";
        }
    }
}
