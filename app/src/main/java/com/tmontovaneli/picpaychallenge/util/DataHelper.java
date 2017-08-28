package com.tmontovaneli.picpaychallenge.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tmontovaneli on 23/08/17.
 */

public class DataHelper {

    private static final SimpleDateFormat _MM_YYYY;

    private static final SimpleDateFormat _MM_YY;

    static {
        _MM_YYYY = new SimpleDateFormat("MM/yyyy");
        _MM_YYYY.setLenient(false);

        _MM_YY = new SimpleDateFormat("MM/yy");
        _MM_YY.setLenient(false);
    }

    public static Date parseMMyyyy(String temp) {
        if (StringHelper.isEmpty(temp))
            return null;


        try {
            return _MM_YYYY.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatMMyyyy(Date date) {
        return _MM_YYYY.format(date);
    }

    public static String formatMMBrYY(Date date) {
        if (date == null)
            return null;

        return _MM_YY.format(date);
    }
}
